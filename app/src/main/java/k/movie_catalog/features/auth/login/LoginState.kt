package k.movie_catalog.features.auth.login

import k.movie_catalog.repositories.models.LoginCredential

data class LoginState(
    val loginCredential: LoginCredential,
    val error: String? = null,
    val isLoading: Boolean = false,
)
