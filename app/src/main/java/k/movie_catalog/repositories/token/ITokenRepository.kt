package k.movie_catalog.repositories.token

import kotlinx.coroutines.flow.Flow

interface ITokenRepository {
    suspend fun getToken(): String?
    suspend fun setToken(token: String)
    suspend fun clearToken()
    suspend fun isAuthorized(): Flow<Boolean>
}