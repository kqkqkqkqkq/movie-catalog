package k.moviecatalog.repositories.token

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    val token: Flow<String?>
    suspend fun setToken(token: String)
    suspend fun clearToken()
    suspend fun getToken(): String?
}