package k.movie_catalog.repositories.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import k.movie_catalog.data.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TokenRepository(
    private val dataStore: DataStore<Preferences>,
) : ITokenRepository {
    override val token: Flow<String?> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.TOKEN] }

    override suspend fun setToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = token
        }
    }

    override suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.TOKEN)
        }
    }

    override suspend fun getToken(): String? {
        return dataStore.data.first()[PreferencesKeys.TOKEN]
    }
}