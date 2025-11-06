package k.movie_catalog.features.main.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.favourites.FavouritesRepository
import k.movie_catalog.repositories.movie.MoviesRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository,
    private val favouritesRepository: FavouritesRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _movieDetailState = MutableStateFlow(MovieDetailsUiState())
    val movieDetailState = _movieDetailState.asStateFlow()

    fun loadMovieDetails(id: UUID) {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(isLoading = true) }
            moviesRepository.getMovieDetails(id).onSuccess { movieDetails ->
                _movieDetailState.update { it.copy(movie = movieDetails, isLoading = false) }
            }.onFailure { e ->
                _movieDetailState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}