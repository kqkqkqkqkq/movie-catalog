package k.movie_catalog.features.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.collections.CollectionsRepository
import k.movie_catalog.repositories.favourites.FavouritesRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionsViewModel(
    private val collectionsRepository: CollectionsRepository,
    private val favouritesRepository: FavouritesRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _collectionsState = MutableStateFlow(CollectionsUiState())
    val collectionsState = _collectionsState.asStateFlow()

    init {
        loadCollections()
        observeCollections()
        observeFavourites()
    }

    private fun observeCollections() {
        viewModelScope.launch(dispatcherProvider.io) {
            collectionsRepository.collections.collect { collections ->
                _collectionsState.update {
                    it.copy(
                        collections = collections ?: emptyList(),
                        isLoading = false,
                    )
                }
            }
        }
    }

    private fun observeFavourites() {
        viewModelScope.launch(dispatcherProvider.io) {
            favouritesRepository.getFavouritesFlow().collect { favourites ->
                _collectionsState.update {
                    it.copy(
                        favourites = favourites,
                        isLoading = false,
                    )
                }
            }
        }
    }

    private fun loadCollections() {
        viewModelScope.launch(dispatcherProvider.io) {
            try {
                _collectionsState.update { it.copy(isLoading = true) }
                val collections = collectionsRepository.getCollections()
                _collectionsState.update {
                    it.copy(
                        collections = collections,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _collectionsState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false,
                    )
                }
            }
        }
    }
}