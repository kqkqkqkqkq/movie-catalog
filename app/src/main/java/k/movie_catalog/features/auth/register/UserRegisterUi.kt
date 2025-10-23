package k.movie_catalog.features.auth.register

import k.movie_catalog.repositories.models.Gender
import k.movie_catalog.repositories.models.UserRegister
import java.time.LocalDateTime

data class UserRegisterUi(
    val username: String = "",
    val name: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val email: String = "",
    val birthDate: String? = null,
    val gender: Gender = Gender.UNKNOW,
) {
    // TODO("create mapper")
    fun toUserRegister() = UserRegister(
        username = this.username,
        name = this.name,
        password = this.password,
        email = this.email,
        birthDate = LocalDateTime.of(
            1, 1, 1, 0, 0, 0
        ),
        gender = this.gender,
    )
}
