package k.movie_catalog.api.schemas

import k.movie_catalog.utils.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserShortDto(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val nickName: String?,
    val avatar: String?,
)
