package k.movie_catalog.api.schemas

import k.movie_catalog.api.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ReviewShortDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val rating: Int,
)
