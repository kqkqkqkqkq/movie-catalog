package k.movie_catalog.repositories.models

import java.io.Serializable
import java.util.UUID

data class CollectionMovie(
    val title: String? = null,
    val description: String? = null,
    val movieId: UUID? = null,
): Serializable