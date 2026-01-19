package k.moviecatalog.features.collections

import k.moviecatalog.repositories.models.Collection

data class CollectionsUiState(
    val collections: List<Collection>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)