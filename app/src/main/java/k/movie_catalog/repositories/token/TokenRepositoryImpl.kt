package k.movie_catalog.repositories.token

import androidx.datastore.core.DataStore
import k.movie_catalog.data.token.models.TokenPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenRepositoryImpl(
    private val tokenStore: DataStore<TokenPreferences>,
) : TokenRepository {
    override val token: Flow<String?> = tokenStore.data.map { it.token }

    override suspend fun setToken(token: String) {
        tokenStore.updateData {
            TokenPreferences(token = token)
        }
    }

    override suspend fun clearToken() {
        tokenStore.updateData {
            TokenPreferences(token = null)
        }
    }

    override suspend fun getToken(): String? {
        return tokenStore.data.first().token
    }
}