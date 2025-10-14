package k.movie_catalog.api.schemas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentialDto(
    @SerialName("username")
    val userName: String?,
    val password: String?,
)