package k.movie_catalog.api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    val token: String,
)