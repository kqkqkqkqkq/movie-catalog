package k.movie_catalog.data.collections.models

import kotlinx.serialization.Serializable

@Serializable
data class CollectionsPreferences(
    val collections: List<CollectionPreferences>? = null
)
