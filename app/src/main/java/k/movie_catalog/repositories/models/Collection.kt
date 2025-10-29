package k.movie_catalog.repositories.models

import java.io.Serializable

data class Collection(
    val title: String = "",
    val movies: List<CollectionMovie>? = null
): Serializable