package k.movie_catalog.api.schemas

import k.movie_catalog.utils.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MovieElementDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<GenreDto>,
    val reviews: List<ReviewShortDto>,
)
