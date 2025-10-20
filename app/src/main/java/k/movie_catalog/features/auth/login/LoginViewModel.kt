package k.movie_catalog.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.auth.IAuthRepository
import k.movie_catalog.repositories.token.ITokenRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: IAuthRepository,
    private val tokenRepository: ITokenRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState = _loginState.asStateFlow()

    fun login() {
        viewModelScope.launch(dispatcherProvider.io) {
            _loginState.update { it.copy(isLoading = true) }
            try {
                val validationResult = validateForm()
                if (!validationResult) {
                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            error = "Password is incorrect",
                        )
                    }
                } else {
                    _loginState.update { it.copy(error = null) }
                    val result = authRepository.login(_loginState.value.loginCredential)

                    if (result.isFailure) {
                        _loginState.update {
                            it.copy(
                                isLoading = false,
                                error = result.exceptionOrNull()?.message ?: "Login failed",
                            )
                        }
                    } else {
                        val token = result.getOrThrow()
                        tokenRepository.setToken(token.token)
                        _loginState.update {
                            it.copy(
                                loginCredential = it.loginCredential.copy(
                                    password = "",
                                ),
                                isLoading = false,
                                error = null,
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error",
                    )
                }
            }
        }
    }

    fun updateUsername(username: String) {
        viewModelScope.launch(dispatcherProvider.default) {
            _loginState.update {
                _loginState.value.copy(
                    loginCredential = _loginState.value.loginCredential.copy(
                        username = username,
                    ),
                )
            }
        }
    }

    fun updatePassword(password: String) {
        viewModelScope.launch(dispatcherProvider.default) {
            _loginState.update {
                _loginState.value.copy(
                    loginCredential = _loginState.value.loginCredential.copy(
                        password = password,
                    ),
                )
            }
        }
    }


    private fun validateForm(): Boolean {
        val state = _loginState.value.loginCredential
        return state.username.isNotBlank() && state.password.isNotBlank()
    }
}