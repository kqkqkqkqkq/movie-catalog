package k.movie_catalog.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import k.movie_catalog.constants.Constants.MOVIE_CATALOG_PREFERENCES_NAME

private val Context.dataStore by preferencesDataStore(
    name = MOVIE_CATALOG_PREFERENCES_NAME,
)

fun getMovieCatalogStore(context: Context) = context.dataStore