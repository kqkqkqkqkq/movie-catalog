package k.movie_catalog.data.token

import kotlinx.serialization.Serializable

@Serializable
data class TokenPreferences(
    val token: String? = null,
)