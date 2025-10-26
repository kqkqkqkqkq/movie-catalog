package k.movie_catalog.data.token

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import k.movie_catalog.constants.DataStoreConstants

class TokenStore(
    private val context: Context,
) {

    private val Context.dataStore by dataStore(
        fileName = DataStoreConstants.MOVIE_CATALOG_PREFERENCES_NAME,
        serializer = TokenPreferencesSerializer,
    )

    fun getMovieCatalogStore(): DataStore<TokenPreferences> = context.dataStore
}