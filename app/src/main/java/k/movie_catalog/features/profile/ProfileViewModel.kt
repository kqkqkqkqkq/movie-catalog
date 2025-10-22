package k.movie_catalog.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.auth.IAuthRepository
import k.movie_catalog.repositories.token.ITokenRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: IAuthRepository,
    private val tokenRepository: ITokenRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileUiState())
    val profileState = _profileState.asStateFlow()

    init {
        getProfile()
    }

    fun logout() {
        viewModelScope.launch(dispatcherProvider.io) {
            _profileState.update { it.copy(isLoading = true) }
            try {
                authRepository.logout()
                tokenRepository.clearToken()
                _profileState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _profileState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknow error",
                    )
                }
            }
        }
    }

    private fun getProfile() {
        viewModelScope.launch(dispatcherProvider.io) {
            _profileState.update { it.copy(isLoading = true) }
            try {
                val token = requireNotNull(tokenRepository.getToken())
                val result = authRepository.getProfile(token)
                if (result.isSuccess) {
                    _profileState.update {
                        it.copy(
                            profile = result.getOrNull(),
                            isLoading = false,
                        )
                    }
                } else {
                    _profileState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exceptionOrNull()?.message ?: "Something went wrong",
                        )
                    }
                }
                _profileState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _profileState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknow error",
                    )
                }
            }
        }
    }
}