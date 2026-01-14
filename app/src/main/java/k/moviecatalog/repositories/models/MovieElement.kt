package k.moviecatalog.repositories.models

import java.util.UUID

data class MovieElement(
    val id: UUID,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<Genre>,
    val reviews: List<ReviewShort>,
)
