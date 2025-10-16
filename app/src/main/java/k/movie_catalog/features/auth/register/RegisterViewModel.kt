package k.movie_catalog.features.auth.register

import androidx.lifecycle.ViewModel
import k.movie_catalog.repositories.auth.IAuthRepository

class RegisterViewModel(
    private val authRepository: IAuthRepository,
): ViewModel() {

//    private val _registerState = MutableStateFlow<RegisterState?>(null)
//    val registerState = _registerState.asStateFlow()

}
//val user = UserRegister(
//    userName = "1test",
//    name = "test",
//    password = "123456",
//    email = "test@mail.com",
//    birthDate = LocalDateTime.of(2000, 1, 1, 12, 30, 45),
//    gender = Gender.MALE,
//)
//val result = authRepository.register(user)
//println(result)