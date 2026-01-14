package k.moviecatalog.data.collections.models

import kotlinx.serialization.Serializable

@Serializable
data class CollectionsPreferences(
    val collections: List<CollectionPreferences>? = null
)
