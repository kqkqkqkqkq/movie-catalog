package k.movie_catalog.features.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.auth.IAuthRepository
import k.movie_catalog.repositories.token.ITokenRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: IAuthRepository,
    private val tokenRepository: ITokenRepository,
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
        println(_registerState.value.user)
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
        return true
    }
}
