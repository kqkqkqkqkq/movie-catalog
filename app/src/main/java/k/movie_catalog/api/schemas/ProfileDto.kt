package k.movie_catalog.api.schemas

import k.movie_catalog.api.serializer.LocalDateTimeSerializer
import k.movie_catalog.api.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class ProfileDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val nickName: String?,
    val email: String,
    val avatarLink: String?,
    val name: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val birthDate: LocalDateTime,
    val genderDto: GenderDto,
)