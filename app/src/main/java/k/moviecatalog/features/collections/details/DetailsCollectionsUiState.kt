package k.moviecatalog.features.collections.details

import k.moviecatalog.repositories.models.Collection

data class DetailsCollectionsUiState(
    val collection: Collection? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)