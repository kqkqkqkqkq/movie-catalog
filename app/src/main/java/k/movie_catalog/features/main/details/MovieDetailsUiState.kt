package k.movie_catalog.features.main.details

import k.movie_catalog.repositories.models.Collection
import k.movie_catalog.repositories.models.MovieDetails

data class MovieDetailsUiState(
    val movie: MovieDetails? = null,
    val inFavourites: Boolean = false,
    val availableCollections: List<Collection>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)