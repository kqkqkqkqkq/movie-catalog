package k.moviecatalog.features.collections.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.moviecatalog.App
import k.moviecatalog.repositories.collections.CollectionsRepository
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.CollectionMovie
import k.moviecatalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsCollectionsViewModel(
    private val collectionsRepository: CollectionsRepository = App.instance.collectionsRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
) : ViewModel() {

    private val _detailsCollectionsState = MutableStateFlow(DetailsCollectionsUiState())
    val detailsCollectionsState = _detailsCollectionsState.asStateFlow()

    fun loadCollection(collection: Collection) {
        viewModelScope.launch(dispatcherProvider.io) {
            _detailsCollectionsState.update { it.copy(isLoading = true) }
            collectionsRepository.getCollections().onSuccess { collections ->
                _detailsCollectionsState.update {
                    it.copy(
                        collection = collections?.first { c ->
                            c.title == collection.title
                        },
                        isLoading = false,
                    )
                }
                _detailsCollectionsState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun removeMovieFromCollection(movie: CollectionMovie) {
        viewModelScope.launch(dispatcherProvider.io) {
            try {
                _detailsCollectionsState.update { it.copy(isLoading = true) }
                collectionsRepository.removeMovieFromCollection(
                    requireNotNull(
                        _detailsCollectionsState.value.collection
                    ), movie
                )
                _detailsCollectionsState.update {
                    it.copy(
                        collection = _detailsCollectionsState.value.collection?.copy(
                            movies = _detailsCollectionsState.value.collection?.movies?.minus(movie)
                        ),
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _detailsCollectionsState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false,
                    )
                }
            }
        }
    }
}