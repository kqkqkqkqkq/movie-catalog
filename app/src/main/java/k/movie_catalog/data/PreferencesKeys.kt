package k.movie_catalog.data

import androidx.datastore.preferences.core.stringPreferencesKey
import k.movie_catalog.constants.DataStoreConstants.TOKEN_KEY

object PreferencesKeys {
    val TOKEN = stringPreferencesKey(TOKEN_KEY)
}