package k.movie_catalog.api.schemas

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserShortDto(
    val userId: UUID,
    val nickName: String?,
    val avatar: String?,
)
