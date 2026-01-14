package k.moviecatalog.features.collections

import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.MovieElement

data class CollectionsUiState(
    val favourites: List<MovieElement> = emptyList(),
    val collections: List<Collection>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)