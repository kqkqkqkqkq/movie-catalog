package k.movie_catalog.data.token

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import k.movie_catalog.constants.DataStoreConstants
import k.movie_catalog.data.token.models.TokenPreferences

class TokenStore(
    private val context: Context,
) {

    private val Context.tokenStore by dataStore(
        fileName = DataStoreConstants.TOKEN_PREFERENCES_STORE,
        serializer = TokenPreferencesSerializer,
    )

    fun getTokenStore(): DataStore<TokenPreferences> = context.tokenStore
}