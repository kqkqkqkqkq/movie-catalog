package k.moviecatalog.api.schemas

import k.moviecatalog.api.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MovieDetailsDto(
    @Serializable(with = UUIDSerializer::class)
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
