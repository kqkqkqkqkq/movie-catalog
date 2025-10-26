package k.movie_catalog.repositories.token

import androidx.datastore.core.DataStore
import k.movie_catalog.data.token.TokenPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenRepository(
    private val dataStore: DataStore<TokenPreferences>,
) : ITokenRepository {
    override val token: Flow<String?> = dataStore.data.map { it.token }

    override suspend fun setToken(token: String) {
        dataStore.updateData {
            TokenPreferences(token = token)
        }
    }

    override suspend fun clearToken() {
        dataStore.updateData {
            TokenPreferences(token = null)
        }
    }

    override suspend fun getToken(): String? {
        return dataStore.data.first().token
    }
}