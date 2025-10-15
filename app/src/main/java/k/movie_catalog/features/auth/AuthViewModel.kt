package k.movie_catalog.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import k.movie_catalog.Screens
import k.movie_catalog.repositories.IAuthRepository
import k.movie_catalog.repositories.models.Gender
import k.movie_catalog.repositories.models.UserRegister
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AuthViewModel(
    private val router: Router,
    private val authRepository: IAuthRepository,
) : ViewModel() {

    fun onOpenRegister() {
        router.navigateTo(Screens.Register())
    }

    fun onOpenLogin() {
        router.navigateTo(Screens.Login())
    }

    fun onBackPressed() {
        router.exit()
    }

    fun testRegister() {
        viewModelScope.launch {
            val user = UserRegister(
                userName = "1test",
                name = "test",
                password = "123456",
                email = "test@mail.com",
                birthDate = LocalDateTime.of(2000, 1, 1, 12, 30, 45),
                gender = Gender.MALE,
            )
            val result = authRepository.register(user)
            println(result)
        }
    }
}