package k.moviecatalog.features.collections.details

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
import k.moviecatalog.R
import k.moviecatalog.common.ui.swipe.SwipeHelper
import k.moviecatalog.common.ui.swipe.UnderlayButton
import k.moviecatalog.databinding.FragmentCollectionsDetailsBinding
import k.moviecatalog.di.AppComponentImpl
import k.moviecatalog.repositories.models.CollectionMovie
import kotlinx.coroutines.launch

class DetailsCollectionsFragment : Fragment(R.layout.fragment_collections_details) {

    private val args: DetailsCollectionsFragmentArgs by navArgs()

    private val viewModel: DetailsCollectionsViewModel by viewModels {
        AppComponentImpl.viewModelFactory {
            DetailsCollectionsViewModel()
        }
    }

    private var _binding: FragmentCollectionsDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DetailsCollectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionsDetailsBinding.bind(view)
        viewModel.loadCollection(args.collection)
        setupRecyclerView()
        setupButtons()
        setupText()
        observeViewModel()
        setupEditIcon()
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

        val recyclerView = binding.movieRecycler
        val swipeHelper = object : SwipeHelper(recyclerView) {
            override fun instantiateButton(position: Int): List<UnderlayButton> {
                return listOf(
                    UnderlayButton(
                        context = recyclerView.context,
                        title = getString(R.string.delete),
                        colorRes = R.color.errorDark,
                        iconRes = R.drawable.icon_trash,
                        clickListener = {
                            val movie = adapter.currentList[position]
                            viewModel.removeMovieFromCollection(movie)
                            adapter.notifyItemRemoved(position)
                        }
                    )
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelper)
        itemTouchHelper.attachToRecyclerView(recyclerView)
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
        binding.editBtn.isVisible = !args.collection.isFavourite
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