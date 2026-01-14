package k.moviecatalog.features.main

import k.moviecatalog.repositories.models.MovieElement
import k.moviecatalog.repositories.models.MoviesPagedList

data class MainUiState(
    val favourites: List<MovieElement> = emptyList(),
    val movies: MoviesPagedList? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)