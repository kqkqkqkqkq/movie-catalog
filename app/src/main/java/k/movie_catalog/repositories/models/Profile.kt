package k.movie_catalog.repositories.models

import java.time.LocalDateTime
import java.util.UUID

data class Profile(
    val id: UUID,
    val nickName: String?,
    val email: String,
    val avatarLink: String?,
    val name: String,
    val birthDate: LocalDateTime,
    val gender: Gender,
)
