package k.movie_catalog.features.collections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import k.movie_catalog.App
import k.movie_catalog.R
import k.movie_catalog.databinding.FragmentCollectionsBinding
import k.movie_catalog.di.AppComponentImpl
import k.movie_catalog.repositories.models.Collection
import k.movie_catalog.repositories.models.CollectionMovie
import kotlinx.coroutines.launch

class CollectionsFragment : Fragment(R.layout.fragment_collections) {

    private val viewModel: CollectionsViewModel by viewModels {
        AppComponentImpl.viewModelFactory {
            CollectionsViewModel(
                collectionsRepository = App.instance.collectionsRepository,
                favouritesRepository = App.instance.favouritesRepository,
                dispatcherProvider = App.instance.dispatcherProvider,
            )
        }
    }

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CollectionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionsBinding.bind(view)
        setupRecyclerView()
        setupButtons()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectionsState.collect { state ->
                    state.collections?.let { collections ->
                        adapter.submitList(collections)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CollectionsAdapter(
            favouriteMovies = viewModel.collectionsState.value.favourites.map { m ->
                CollectionMovie(
                    title = m.name,
                    description = m.name,
                    posterUrl = m.poster,
                    movieId = m.id,
                )
            },
            onFavouriteClick = { collection ->
                onCollectionClicked(collection)
            },
            onCollectionClick = { collection ->
                onCollectionClicked(collection)
            }
        )

        binding.collectionsRecycler.layoutManager = LinearLayoutManager(
            view?.context, LinearLayoutManager.VERTICAL, false,
        )
        binding.collectionsRecycler.adapter = adapter
    }

    private fun onCollectionClicked(collection: Collection) {
        val action = CollectionsFragmentDirections
            .actionCollectionsToDetails(collection)
        findNavController().navigate(action)
    }

    private fun setupButtons() {
        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_collections_to_create)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}