package k.movie_catalog.features.auth.login

import k.movie_catalog.repositories.models.LoginCredential

data class LoginUiState(
    val loginCredential: LoginCredential = LoginCredential(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
