package k.moviecatalog.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.moviecatalog.App
import k.moviecatalog.repositories.auth.AuthRepository
import k.moviecatalog.repositories.token.TokenRepository
import k.moviecatalog.common.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository = App.instance.authRepository,
    private val tokenRepository: TokenRepository = App.instance.tokenRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
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
            authRepository.getProfile().onSuccess { profile ->
                _profileState.update {
                    it.copy(
                        profile = profile,
                        isLoading = false,
                    )
                }
            }.onFailure { e ->
                _profileState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message,
                    )
                }
            }
        }
    }
}