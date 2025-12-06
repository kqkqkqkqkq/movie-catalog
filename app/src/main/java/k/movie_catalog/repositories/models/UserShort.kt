package k.movie_catalog.repositories.models

import java.util.UUID

data class UserShort(
    val userId: UUID,
    val nickName: String?,
    val avatar: String?,
)
