package k.movie_catalog.repositories.models

import java.io.Serializable
import java.util.UUID

data class MovieDetails(
    val id: UUID,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<Genre>,
    val reviews: List<Review>,
    val time: Int,
    val tagline: String?,
    val description: String?,
    val director: String?,
    val budget: Int?,
    val fees: Int?,
    val ageLimit: Int,
) : Serializable
