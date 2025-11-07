package k.movie_catalog.features.collections.create

import k.movie_catalog.repositories.models.Collection

data class CreateCollectionsUiState(
    val collection: Collection = Collection(),
    val isLoading: Boolean = false,
    val error: String? = null,
)