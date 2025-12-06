package k.movie_catalog.features.collections.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.collections.CollectionsRepository
import k.movie_catalog.repositories.models.Collection
import k.movie_catalog.repositories.models.CollectionMovie
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsCollectionsViewModel(
    private val collectionsRepository: CollectionsRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _detailsCollectionsState = MutableStateFlow(DetailsCollectionsUiState())
    val detailsCollectionsState = _detailsCollectionsState.asStateFlow()

    fun loadCollection(collection: Collection) {
        viewModelScope.launch(dispatcherProvider.io) {
            try {
                _detailsCollectionsState.update { it.copy(isLoading = true) }
                _detailsCollectionsState.update {
                    it.copy(
                        collection = collectionsRepository.getCollections()?.first { c ->
                            c.title == collection.title
                        },
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