package k.movie_catalog.repositories.auth

import kotlinx.coroutines.flow.Flow

interface ITokenRepository {
    suspend fun getToken(): String
    suspend fun setToken(token: String)
    suspend fun clearToken()
}