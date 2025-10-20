package k.movie_catalog.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.auth.IAuthRepository
import k.movie_catalog.repositories.token.ITokenRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: IAuthRepository,
    private val tokenRepository: ITokenRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState = _loginState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            _loginState.emit(_loginState.value.copy(isLoading = true))
            delay(1000)
            _loginState.emit(_loginState.value.copy(isLoading = false))
//            try {
//                val validationResult = validateForm()
//                if (!validationResult) {
//                    _loginState.emit(
//                        _loginState.value.copy(
//                            isLoading = false,
//                            error = "Password is incorrect"
//                        )
//                    )
//                } else {
//                    _loginState.emit(_loginState.value.copy(error = null))
//                    val result = authRepository.login(_loginState.value.loginCredential)
//
//                    if (result.isSuccess) {
//                        _navigationEvent.emit(NavigationEvent.NavigateToMain)
//                    } else {
//                        _loginState.emit(
//                            _loginState.value.copy(
//                                isLoading = false,
//                                error = "Login failed",
//                            )
//                        )
//                    }
//                }
//            } catch (e: Exception) {
//                _loginState.emit(
//                    _loginState.value.copy(
//                        isLoading = false,
//                        error = e.message ?: "Unknown error",
//                    )
//                )
//            }
        }
    }

    fun updateUsername(username: String) {
        viewModelScope.launch {
            _loginState.emit(
                _loginState.value.copy(
                    loginCredential = _loginState.value.loginCredential.copy(
                        username = username,
                    ),
                    error = null,
                )
            )
        }
    }

    fun updatePassword(password: String) {
        viewModelScope.launch {
            _loginState.emit(
                _loginState.value.copy(
                    loginCredential = _loginState.value.loginCredential.copy(
                        password = password,
                    ),
                    error = null,
                )
            )
        }
    }


    private fun validateForm(): Boolean {
        return true
    }
}