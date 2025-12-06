package k.movie_catalog.features.collections.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentCollectionsDetailsBinding
import k.movie_catalog.di.AppComponentImpl
import k.movie_catalog.repositories.models.CollectionMovie
import kotlinx.coroutines.launch

class DetailsCollectionsFragment : Fragment(R.layout.fragment_collections_details) {

    private val args: DetailsCollectionsFragmentArgs by navArgs()

    private val viewModel: DetailsCollectionsViewModel by viewModels {
        AppComponentImpl.viewModelFactory {
            DetailsCollectionsViewModel(
                collectionsRepository = App.instance.collectionsRepository,
                dispatcherProvider = App.instance.dispatcherProvider,
            )
        }
    }

    private var _binding: FragmentCollectionsDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DetailsCollectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionsDetailsBinding.bind(view)
        viewModel.loadCollection(args.collection)
        println(viewModel.detailsCollectionsState.value.collection)
        setupRecyclerView()
        setupButtons()
        setupText()
        setupEditIcon()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailsCollectionsState.collect { state ->
                    state.collection?.movies?.let { movies ->
                        adapter.submitList(movies)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = DetailsCollectionAdapter { movie ->
            onCollectionMovieClick(movie)
        }

        binding.movieRecycler.layoutManager = LinearLayoutManager(
            view?.context, LinearLayoutManager.VERTICAL, false,
        )
        binding.movieRecycler.adapter = adapter
        swipeToDelete(binding.movieRecycler) { position ->
            val movie = adapter.currentList[position]
            viewModel.removeMovieFromCollection(movie)
            adapter.notifyItemRemoved(position)
        }
    }

    private fun setupButtons() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.editBtn.setOnClickListener {
            val action = DetailsCollectionsFragmentDirections
                .actionCollectionsDetailsToEdit(args.collection)
            findNavController().navigate(action)
        }
    }

    private fun setupText() {
        binding.title.text = args.collection.title
    }

    private fun setupEditIcon() {
        binding.editBtn.isVisible =
            viewModel.detailsCollectionsState.value.collection?.isFavourite ?: false
    }

    private fun swipeToDelete(
        recyclerView: RecyclerView,
        onItemSwiped: (position: Int) -> Unit
    ) {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                onItemSwiped(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun onCollectionMovieClick(movie: CollectionMovie) {
        val action = DetailsCollectionsFragmentDirections
            .actionCollectionsDetailsToMovieDetails(movie.movieId.toString())
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}