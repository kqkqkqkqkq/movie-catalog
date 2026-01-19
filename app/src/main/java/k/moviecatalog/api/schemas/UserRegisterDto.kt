package k.moviecatalog.api.schemas

import k.moviecatalog.utils.serializer.GenderDtoSerializer
import k.moviecatalog.utils.serializer.LocalDateTimeSerializer
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