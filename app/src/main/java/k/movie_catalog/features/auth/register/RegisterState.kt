package k.movie_catalog.features.auth.register

import k.movie_catalog.repositories.models.UserRegister

data class RegisterState(
    val user: UserRegister,
    val error: String? = null,
    val isLoading: Boolean = false,
)
