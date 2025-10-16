package k.movie_catalog.features.auth.login

import androidx.lifecycle.ViewModel
import k.movie_catalog.repositories.IAuthRepository
import k.movie_catalog.repositories.models.LoginCredential
import k.movie_catalog.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
    private val authRepository: IAuthRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val loginState = _loginState.asStateFlow()

    fun login() {

    }

    fun updateLoginState(newState: LoginCredential) {
        _loginState.value = ScreenState.Content(newState)
    }
}