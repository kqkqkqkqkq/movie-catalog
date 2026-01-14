package k.moviecatalog.features.auth.register

data class RegisterUiState(
    val user: UserRegisterUi = UserRegisterUi(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
