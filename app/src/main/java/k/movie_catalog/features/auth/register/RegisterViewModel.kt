package k.movie_catalog.features.auth.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.auth.AuthRepository
import k.movie_catalog.repositories.models.Gender
import k.movie_catalog.repositories.token.TokenRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
    private val dispatcherProvider: DispatcherProvider,
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
                authRepository.register(
                    _registerState.value.user.toUserRegister()
                ).onSuccess { token ->
                    tokenRepository.setToken(token.token)
                    _registerState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                        )
                    }
                }.onFailure { e ->
                    _registerState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message,
                        )
                    }
                }
            } else {
                _registerState.update {
                    it.copy(
                        isLoading = false,
                        error = "Form doesn't valid",
                    )
                }
            }
        }
    }

    fun validateRegisterForm(): Boolean {
        val user = _registerState.value.user
        val minPasswordLength = 6
        return user.name.isNotBlank() &&
                user.username.isNotBlank() &&
                user.email.isNotBlank() &&
                user.password.isNotBlank() &&
                user.passwordRepeat.isNotBlank() &&
                user.birthDate != null &&
                user.gender != Gender.UNKNOW &&
                user.password == user.passwordRepeat &&
                user.password.length >= minPasswordLength &&
                Patterns.EMAIL_ADDRESS.matcher(user.email).matches()
    }
}
