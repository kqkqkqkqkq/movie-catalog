package k.moviecatalog.api.schemas

import k.moviecatalog.utils.serializer.LocalDateTimeSerializer
import k.moviecatalog.utils.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class ReviewDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val rating: Int,
    val reviewText: String?,
    val isAnonymous: Boolean,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDateTime: LocalDateTime,
    val author: UserShortDto?,
)
