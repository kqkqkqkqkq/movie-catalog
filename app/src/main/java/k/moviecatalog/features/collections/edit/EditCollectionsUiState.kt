package k.moviecatalog.features.collections.edit

import k.moviecatalog.repositories.models.Collection

data class EditCollectionsUiState(
    val collection: Collection = Collection(),
    val isLoading: Boolean = false,
    val error: String? = null,
)