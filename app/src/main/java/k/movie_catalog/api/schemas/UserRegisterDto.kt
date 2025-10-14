package k.movie_catalog.api.schemas

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class UserRegisterDto(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: Date,
    val gender: Gender,
)
