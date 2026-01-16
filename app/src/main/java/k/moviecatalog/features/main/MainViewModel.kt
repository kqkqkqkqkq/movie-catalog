package k.moviecatalog.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.moviecatalog.App
import k.moviecatalog.repositories.favourites.FavouritesRepository
import k.moviecatalog.repositories.models.MovieElement
import k.moviecatalog.repositories.movie.MoviesRepository
import k.moviecatalog.common.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val moviesRepository: MoviesRepository = App.instance.moviesRepository,
    private val favouritesRepository: FavouritesRepository = App.instance.favouritesRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainUiState())
    val mainState = _mainState.asStateFlow()

    init {
        loadMovies()
        observeFavourites()
        loadInitialFavourites()
    }

    private fun loadInitialFavourites() {
        viewModelScope.launch(dispatcherProvider.io) {
            favouritesRepository.getFavourites()
        }
    }

    private fun observeFavourites() {
        viewModelScope.launch(dispatcherProvider.io) {
            favouritesRepository.getFavouritesFlow().collect { favourites ->
                _mainState.update {
                    it.copy(favourites = favourites, isLoading = false)
                }
            }
        }
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

    fun deleteFavourite(movie: MovieElement) {
        viewModelScope.launch(dispatcherProvider.io) {
            favouritesRepository.deleteFavourite(movie)
        }
    }
}