package k.movie_catalog.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.favourites.FavouritesRepository
import k.movie_catalog.repositories.movie.MoviesRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val moviesRepository: MoviesRepository,
    private val favouritesRepository: FavouritesRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainUiState())
    val mainState = _mainState.asStateFlow()

    init {
        loadMovies()
        loadFavourites()
    }

    fun loadNextPage() {
        val currentPage = _mainState.value.movies?.pageInfo?.currentPage ?: 1
        val totalPages = _mainState.value.movies?.pageInfo?.pageCount ?: 1

        if (currentPage < totalPages) {
            loadMovies(currentPage + 1)
        }
    }

    private fun loadMovies(page: Int = 1) {
        viewModelScope.launch(dispatcherProvider.io) {
            _mainState.update { it.copy(isLoading = true) }
            moviesRepository.getMovie(page).onSuccess { newPagedMovies ->
                _mainState.update { oldState ->
                    val oldMovies = oldState.movies?.movies ?: emptyList()
                    val mergedMovies = if (page == 1) {
                        newPagedMovies.movies
                    } else {
                        oldMovies + newPagedMovies.movies
                    }
                    oldState.copy(
                        movies = newPagedMovies.copy(movies = mergedMovies),
                        error = null,
                    )
                }
            }.onFailure { e ->
                _mainState.update { it.copy(error = e.message) }
            }
            _mainState.update { it.copy(isLoading = false) }
        }
    }

    private fun loadFavourites() {
        viewModelScope.launch(dispatcherProvider.io) {
            _mainState.update { it.copy(isLoading = true) }
            favouritesRepository.getFavourites().onSuccess { favourites ->
                _mainState.update { it.copy(
                    favourites = favourites,
                ) }
            }.onFailure { e ->
                _mainState.update { it.copy(error = e.message) }
            }
            _mainState.update { it.copy(isLoading = false) }
        }
    }
}