package k.movie_catalog.features.collections.details

import k.movie_catalog.repositories.models.Collection
import k.movie_catalog.repositories.models.CollectionMovie

data class DetailsCollectionsUiState(
    val collection: Collection? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)