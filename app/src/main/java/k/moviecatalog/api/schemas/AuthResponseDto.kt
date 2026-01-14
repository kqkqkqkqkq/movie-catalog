package k.moviecatalog.api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    val token: String,
)