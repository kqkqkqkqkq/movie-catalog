package k.moviecatalog.features.collections.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.moviecatalog.App
import k.moviecatalog.common.dispatcher.DispatcherProvider
import k.moviecatalog.repositories.collections.CollectionsRepository
import k.moviecatalog.repositories.favourites.FavouritesRepository
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.CollectionMovie
import k.moviecatalog.utils.mapper.movie.toCollectionMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsCollectionsViewModel(
    private val favouritesRepository: FavouritesRepository = App.instance.favouritesRepository,
    private val collectionsRepository: CollectionsRepository = App.instance.collectionsRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
) : ViewModel() {

    private val _detailsCollectionsState = MutableStateFlow(DetailsCollectionsUiState())
    val detailsCollectionsState = _detailsCollectionsState.asStateFlow()

    fun loadCollection(collection: Collection) {
        viewModelScope.launch(dispatcherProvider.io) {
            _detailsCollectionsState.update { it.copy(isLoading = true) }
            if (collection.isFavourite) {
                // TODO("fix: пофиксить удаление и обновление фильмов")
                favouritesRepository.getFavouritesFlow().collect { favourites ->
                    val movies = favourites.map { it.toCollectionMovie() }
                    _detailsCollectionsState.update {
                        it.copy(collection = collection.copy(movies = movies))
                    }
                }
            } else {
                collectionsRepository.getCollections().onSuccess { collections ->
                    val currentCollection = collections?.first { c -> c.title == collection.title }
                    _detailsCollectionsState.update {
                        it.copy(collection = currentCollection)
                    }
                }
            }
            _detailsCollectionsState.update { it.copy(isLoading = false) }
        }
    }

    fun removeMovieFromCollection(movie: CollectionMovie) {
        viewModelScope.launch(dispatcherProvider.io) {
            try {
                _detailsCollectionsState.update { it.copy(isLoading = true) }
                val collection = requireNotNull(_detailsCollectionsState.value.collection)
                if (collection.isFavourite) {
                    favouritesRepository.deleteFavourite(requireNotNull(movie.movieId))
                        .onFailure { e ->
                            _detailsCollectionsState.update { it.copy(error = e.message) }
                        }
                } else {
                    collectionsRepository.removeMovieFromCollection(
                        requireNotNull(
                            _detailsCollectionsState.value.collection
                        ), movie
                    )
                    _detailsCollectionsState.update {
                        it.copy(
                            collection = collection.copy(
                                movies = requireNotNull(collection.movies) - movie
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                _detailsCollectionsState.update { it.copy(error = e.message) }
            } finally {
                _detailsCollectionsState.update { it.copy(isLoading = false) }
            }
        }
    }
}