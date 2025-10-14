package k.movie_catalog.api.schemas

import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

@Serializable
data class ProfileDto(
    val id: UUID,
    val nickName: String?,
    val email: String,
    val avatarLink: String?,
    val name: String,
    val birthDate: Date,
    val gender: Gender,
)
