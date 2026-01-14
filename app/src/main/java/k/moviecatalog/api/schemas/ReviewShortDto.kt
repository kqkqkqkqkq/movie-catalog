package k.moviecatalog.api.schemas

import k.moviecatalog.api.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ReviewShortDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val rating: Int,
)
