package k.movie_catalog.features.collections

import k.movie_catalog.repositories.models.Collection

data class CollectionsUiState(
    val collections: List<Collection>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)