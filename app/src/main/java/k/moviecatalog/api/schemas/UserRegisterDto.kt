package k.moviecatalog.api.schemas

import k.moviecatalog.api.serializer.GenderDtoSerializer
import k.moviecatalog.api.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserRegisterDto(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val birthDate: LocalDateTime,
    @Serializable(with = GenderDtoSerializer::class)
    val gender: GenderDto?,
)