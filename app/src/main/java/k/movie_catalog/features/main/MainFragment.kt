package k.movie_catalog.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentMainBinding
import k.movie_catalog.di.AppComponentImpl
import k.movie_catalog.repositories.models.MovieElement
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        AppComponentImpl.viewModelFactory {
            MainViewModel(
                moviesRepository = App.instance.moviesRepository,
                favouritesRepository = App.instance.favouritesRepository,
                dispatcherProvider = App.instance.dispatcherProvider,
            )
        }
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var favouritesAdapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainState.collect { state ->
                    state.movies?.let { movies ->
                        moviesAdapter.submitList(movies.movies - movies.movies.first())
                    }
                    favouritesAdapter.submitList(state.favourites)
                    val mainMovie = state.movies?.movies?.first()
                    if (mainMovie != null) {
                        setupImages(mainMovie)
                        setupButtons(mainMovie)
                    }
                    binding.favouriteRecycler.isVisible = state.favourites.isNotEmpty()
                    binding.favouritesLabel.isVisible = state.favourites.isNotEmpty()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter { movie ->
            onMovieClicked(movie)
        }
        favouritesAdapter = FavouritesAdapter(
            onFavouriteClick = { movie ->
                onMovieClicked(movie)
            },
            onDeleteCLick = { movie ->
                onFavouriteDelete(movie)
            }
        )

        val movieLayoutManager =
            LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)
        val favouriteLayoutManager =
            LinearLayoutManager(view?.context, LinearLayoutManager.HORIZONTAL, false)

        binding.movieRecycler.layoutManager = movieLayoutManager
        binding.movieRecycler.adapter = moviesAdapter

        binding.favouriteRecycler.layoutManager = favouriteLayoutManager
        binding.favouriteRecycler.adapter = favouritesAdapter

        binding.movieRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = movieLayoutManager.itemCount
                val lastVisibleItem = movieLayoutManager.findLastVisibleItemPosition()

                if (lastVisibleItem >= totalItemCount - 5) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    private fun setupButtons(mainMovie: MovieElement) {
        binding.watchButton.setOnClickListener {
            onMovieClicked(mainMovie)
        }
    }

    private fun setupImages(mainMovie: MovieElement) {
        binding.moviePoster.load(mainMovie.poster) {
            crossfade(true)
            placeholder(R.drawable.icon_movie_catalog)
            error(R.drawable.icon_movie_catalog)
        }
    }

    private fun onMovieClicked(movie: MovieElement) {
        val action = MainFragmentDirections
            .actionMainToMovieDetails(movie.id.toString())
        findNavController().navigate(action)
    }

    private fun onFavouriteDelete(movie: MovieElement) {
        viewModel.deleteFavourite(movie)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}