package k.movie_catalog.repositories.models

import java.io.Serializable

data class Collection(
    val icon: Int? = null,
    val title: String = "",
    val movies: List<CollectionMovie>? = null,
    val isFavourite: Boolean = false,
) : Serializable