package k.movie_catalog.repositories.models

import k.movie_catalog.api.schemas.GenreDto
import k.movie_catalog.api.schemas.ReviewDto
import java.util.UUID

data class MovieDetail(
    val id: UUID,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<GenreDto>,
    val reviews: List<ReviewDto>,
    val time: Int,
    val tagline: String?,
    val description: String?,
    val director: String?,
    val budget: Int?,
    val fees: Int?,
    val ageLimit: Int,
)
