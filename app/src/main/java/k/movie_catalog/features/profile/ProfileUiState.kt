package k.movie_catalog.features.profile

import k.movie_catalog.repositories.models.Profile

data class ProfileUiState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
