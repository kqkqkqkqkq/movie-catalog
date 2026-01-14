package k.moviecatalog.features.auth.login

import k.moviecatalog.repositories.models.LoginCredential

data class LoginUiState(
    val loginCredential: LoginCredential = LoginCredential(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
