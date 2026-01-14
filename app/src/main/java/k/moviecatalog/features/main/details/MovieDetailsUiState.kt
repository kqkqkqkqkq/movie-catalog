package k.moviecatalog.features.main.details

import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.MovieDetails

data class MovieDetailsUiState(
    val movie: MovieDetails? = null,
    val inFavourites: Boolean = false,
    val availableCollections: List<Collection>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)