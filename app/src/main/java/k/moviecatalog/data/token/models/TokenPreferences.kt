package k.moviecatalog.data.token.models

import kotlinx.serialization.Serializable

@Serializable
data class TokenPreferences(
    val token: String? = null,
)