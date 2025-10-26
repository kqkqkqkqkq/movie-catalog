package k.movie_catalog.repositories.token

import k.movie_catalog.data.token.TokenPreferences
import kotlinx.coroutines.flow.Flow

interface ITokenRepository {
    val token: Flow<String?>
    suspend fun setToken(token: String)
    suspend fun clearToken()
    suspend fun getToken(): String?
}