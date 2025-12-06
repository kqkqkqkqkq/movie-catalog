package k.movie_catalog.features.collections.details

import k.movie_catalog.repositories.models.Collection

data class DetailsCollectionsUiState(
    val collection: Collection? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)