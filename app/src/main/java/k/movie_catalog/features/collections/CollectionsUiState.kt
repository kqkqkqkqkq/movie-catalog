package k.movie_catalog.features.collections

import k.movie_catalog.repositories.models.Collection
import k.movie_catalog.repositories.models.MovieElement

data class CollectionsUiState(
    val favourites: List<MovieElement> = emptyList(),
    val collections: List<Collection>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)