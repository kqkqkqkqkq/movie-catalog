package k.moviecatalog.features.collections.create

import k.moviecatalog.repositories.models.Collection

data class CreateCollectionsUiState(
    val collection: Collection = Collection(),
    val isLoading: Boolean = false,
    val error: String? = null,
)