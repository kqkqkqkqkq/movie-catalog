package k.moviecatalog.features.auth.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.moviecatalog.App
import k.moviecatalog.common.dispatcher.DispatcherProvider
import k.moviecatalog.constants.UiConstants
import k.moviecatalog.repositories.auth.AuthRepository
import k.moviecatalog.repositories.models.Gender
import k.moviecatalog.repositories.token.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository = App.instance.authRepository,
    private val tokenRepository: TokenRepository = App.instance.tokenRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState = _registerState.asStateFlow()

    fun updateUserState(user: UserRegisterUi) {
        viewModelScope.launch(dispatcherProvider.default) {
            _registerState.update {
                it.copy(user = user)
            }
        }
    }

    fun register() {
        viewModelScope.launch(dispatcherProvider.io) {
            _registerState.update { it.copy(isLoading = true) }
            if (validateRegisterForm()) {
                val user = _registerState.value.user.toUserRegister()
                authRepository.register(user)
                    .onSuccess { token ->
                        tokenRepository.setToken(token.token)
                        _registerState.update { it.copy(error = null) }
                    }.onFailure { e ->
                        _registerState.update { it.copy(error = e.message) }
                    }
            } else {
                _registerState.update { it.copy(error = "Form doesn't valid") }
            }
            _registerState.update { it.copy(isLoading = false) }
        }
    }

    fun validateRegisterForm(): Boolean {
        val user = _registerState.value.user
        return user.name.isNotBlank() &&
                user.username.isNotBlank() &&
                user.email.isNotBlank() &&
                user.password.isNotBlank() &&
                user.passwordRepeat.isNotBlank() &&
                user.birthDate != null &&
                user.gender != Gender.UNKNOW &&
                user.password == user.passwordRepeat &&
                user.password.length >= UiConstants.MIN_PASSWORD_LENGTH &&
                Patterns.EMAIL_ADDRESS.matcher(user.email).matches()
    }
}
