package k.moviecatalog.features.main.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.moviecatalog.App
import k.moviecatalog.repositories.collections.CollectionsRepository
import k.moviecatalog.repositories.favourites.FavouritesRepository
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.movie.MoviesRepository
import k.moviecatalog.utils.dispatcher.DispatcherProvider
import k.moviecatalog.utils.mapper.toCollectionMovie
import k.moviecatalog.utils.mapper.toMovieElement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository = App.instance.moviesRepository,
    private val favouritesRepository: FavouritesRepository = App.instance.favouritesRepository,
    private val collectionsRepository: CollectionsRepository = App.instance.collectionsRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
) : ViewModel() {

    private val _movieDetailState = MutableStateFlow(MovieDetailsUiState())
    val movieDetailState = _movieDetailState.asStateFlow()

    init {
        getAvailableCollections()
    }

    fun onFavourite() {
        val movie = _movieDetailState.value.movie?.toMovieElement() ?: return
        viewModelScope.launch(dispatcherProvider.io) {
            val currentlyFavourite = _movieDetailState.value.inFavourites
            try {
                if (currentlyFavourite) {
                    favouritesRepository.deleteFavourite(movie)
                } else {
                    favouritesRepository.addFavourite(movie)
                }
                _movieDetailState.update {
                    it.copy(
                        inFavourites = !currentlyFavourite,
                    )
                }
            } catch (e: Exception) {
                _movieDetailState.update {
                    it.copy(
                        error = e.message ?: "Favourite update failed",
                    )
                }
            }
        }
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
        }
    }
}