package k.moviecatalog.features.auth.register

import k.moviecatalog.repositories.models.Gender
import k.moviecatalog.repositories.models.UserRegister
import java.time.LocalDateTime

data class UserRegisterUi(
    val username: String = "",
    val name: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val email: String = "",
    val birthDate: LocalDateTime? = null,
    val gender: Gender = Gender.UNKNOW,
) {
    fun toUserRegister() = UserRegister(
        username = this.username,
        name = this.name,
        password = this.password,
        email = this.email,
        birthDate = requireNotNull(this.birthDate),
        gender = this.gender,
    )
}
