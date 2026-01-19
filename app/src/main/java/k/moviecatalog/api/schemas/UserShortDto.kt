package k.moviecatalog.api.schemas

import k.moviecatalog.utils.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserShortDto(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val nickName: String?,
    val avatar: String?,
)
