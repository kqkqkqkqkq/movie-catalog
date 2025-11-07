package k.movie_catalog.features.main

import k.movie_catalog.repositories.models.MovieElement
import k.movie_catalog.repositories.models.MoviesPagedList

data class MainUiState(
    val favourites: List<MovieElement> = emptyList(),
    val movies: MoviesPagedList? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)