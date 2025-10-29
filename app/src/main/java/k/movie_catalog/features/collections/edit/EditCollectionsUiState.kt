package k.movie_catalog.features.collections.edit

import k.movie_catalog.repositories.models.Collection

data class EditCollectionsUiState(
    val collection: Collection? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)