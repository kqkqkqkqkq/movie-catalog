package k.moviecatalog.data.token

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import k.moviecatalog.constants.DataStoreConstants
import k.moviecatalog.data.token.models.TokenPreferences

class TokenStore(
    private val context: Context,
) {

    private val Context.tokenStoreFile by dataStore(
        fileName = DataStoreConstants.TOKEN_PREFERENCES_STORE,
        serializer = TokenPreferencesSerializer,
    )

    fun getTokenStore(): DataStore<TokenPreferences> = context.tokenStoreFile
}