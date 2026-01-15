package k.moviecatalog.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.moviecatalog.App
import k.moviecatalog.repositories.auth.AuthRepository
import k.moviecatalog.repositories.models.LoginCredential
import k.moviecatalog.repositories.token.TokenRepository
import k.moviecatalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository = App.instance.authRepository,
    private val tokenRepository: TokenRepository = App.instance.tokenRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
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
//                    _loginState.update { it.copy(error = null) }
                }.onFailure { e ->
                    _loginState.update { it.copy(error = e.message) }
                }
            }
            _loginState.update { it.copy(isLoading = false) }
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
        val loginCredential = _loginState.value.loginCredential
        return loginCredential.username.isNotBlank() && loginCredential.password.isNotBlank()
    }
}