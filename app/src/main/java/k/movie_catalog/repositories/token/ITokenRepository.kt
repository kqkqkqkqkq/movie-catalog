package k.movie_catalog.repositories.token

import kotlinx.coroutines.flow.Flow

interface ITokenRepository {
    val token: Flow<String?>
    suspend fun setToken(token: String)
    suspend fun clearToken()
    suspend fun getToken(): String?
}