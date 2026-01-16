package k.moviecatalog.features.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.moviecatalog.App
import k.moviecatalog.repositories.collections.CollectionsRepository
import k.moviecatalog.repositories.favourites.FavouritesRepository
import k.moviecatalog.common.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionsViewModel(
    private val collectionsRepository: CollectionsRepository = App.instance.collectionsRepository,
    private val favouritesRepository: FavouritesRepository = App.instance.favouritesRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
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
            _collectionsState.update { it.copy(isLoading = true) }
            collectionsRepository.getCollections().onSuccess { collections ->
                _collectionsState.update { it.copy(collections = collections) }
            }.onFailure {
                _collectionsState.update { it.copy(error = "Error during get collections request") }
            }
            _collectionsState.update { it.copy(isLoading = false) }
        }
    }
}