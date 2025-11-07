package k.movie_catalog.features.main.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.collections.CollectionsRepository
import k.movie_catalog.repositories.favourites.FavouritesRepository
import k.movie_catalog.repositories.models.Collection
import k.movie_catalog.repositories.movie.MoviesRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import k.movie_catalog.utils.mapper.toCollectionMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository,
    private val favouritesRepository: FavouritesRepository,
    private val collectionsRepository: CollectionsRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _movieDetailState = MutableStateFlow(MovieDetailsUiState())
    val movieDetailState = _movieDetailState.asStateFlow()

    init {
        getAvailableCollections()
    }

    fun loadMovieDetails(id: UUID) {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(isLoading = true) }
            moviesRepository.getMovieDetails(id).onSuccess { movieDetails ->
                _movieDetailState.update { it.copy(movie = movieDetails, isLoading = false) }
                favouritesRepository.getFavourites().onSuccess { favourites ->
                    _movieDetailState.update {
                        it.copy(
                            inFavourites = id in favourites.map { fav -> fav.id },
                            isLoading = false,
                        )
                    }
                }.onFailure { e ->
                    _movieDetailState.update { it.copy(error = e.message, isLoading = false) }
                }
            }.onFailure { e ->
                _movieDetailState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun addToCollection(collection: Collection) {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(isLoading = true) }
            val movie = _movieDetailState.value.movie
            if (movie != null) {
                println(movie)
                collectionsRepository.addMovieToCollection(collection, movie.toCollectionMovie())
            } else {
                _movieDetailState.update {
                    it.copy(
                        error = "Movie is null",
                        isLoading = false,
                    )
                }
            }
            _movieDetailState.update { it.copy(isLoading = false) }
        }
    }

    private fun getAvailableCollections() {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(isLoading = true) }
            _movieDetailState.update {
                it.copy(
                    availableCollections = collectionsRepository.getCollections(),
                    isLoading = false,
                )
            }
            println(collectionsRepository.getCollections())
        }
    }
}