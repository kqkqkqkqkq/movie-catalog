package k.movie_catalog.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.auth.AuthRepository
import k.movie_catalog.repositories.models.LoginCredential
import k.movie_catalog.repositories.token.TokenRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState = _loginState.asStateFlow()

    fun login() {
        viewModelScope.launch(dispatcherProvider.io) {
            _loginState.update { it.copy(isLoading = true) }
            if (validateForm()) {
                _loginState.update { it.copy(error = null) }
                authRepository.login(_loginState.value.loginCredential).onSuccess { token ->
                    tokenRepository.setToken(token.token)
                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                        )
                    }
                }.onFailure { e ->
                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message,
                        )
                    }
                }
            } else {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        error = "Password is incorrect",
                    )
                }
            }
        }
    }

    fun updateLoginState(loginCredential: LoginCredential) {
        viewModelScope.launch(dispatcherProvider.default) {
            _loginState.update {
                it.copy(loginCredential = loginCredential)
            }
        }
    }

    fun validateForm(): Boolean {
        val state = _loginState.value.loginCredential
        return state.username.isNotBlank() && state.password.isNotBlank()
    }
}