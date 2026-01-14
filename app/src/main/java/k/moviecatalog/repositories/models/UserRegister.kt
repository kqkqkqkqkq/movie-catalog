package k.moviecatalog.repositories.models

import java.time.LocalDateTime

data class UserRegister(
    val username: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: LocalDateTime,
    val gender: Gender,
)
