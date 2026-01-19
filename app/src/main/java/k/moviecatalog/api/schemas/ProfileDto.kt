package k.moviecatalog.api.schemas

import k.moviecatalog.utils.serializer.GenderDtoSerializer
import k.moviecatalog.utils.serializer.LocalDateTimeSerializer
import k.moviecatalog.utils.serializer.UUIDSerializer
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
    @Serializable(with = GenderDtoSerializer::class)
    val gender: GenderDto?,
)