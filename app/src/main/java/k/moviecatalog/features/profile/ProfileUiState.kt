package k.moviecatalog.features.profile

import k.moviecatalog.repositories.models.Profile

data class ProfileUiState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
