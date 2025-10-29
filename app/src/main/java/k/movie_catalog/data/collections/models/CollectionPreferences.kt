package k.movie_catalog.data.collections.models

import kotlinx.serialization.Serializable

@Serializable
data class CollectionPreferences(
    val title: String? = null,
    val movies: List<MoviePreferences>? = null,
)