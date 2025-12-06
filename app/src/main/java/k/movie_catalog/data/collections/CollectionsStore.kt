package k.movie_catalog.data.collections

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import k.movie_catalog.constants.DataStoreConstants
import k.movie_catalog.data.collections.models.CollectionsPreferences

class CollectionsStore(
    private val context: Context,
) {

    private val Context.collectionsStoreFile by dataStore(
        fileName = DataStoreConstants.COLLECTIONS_PREFERENCES_STORE,
        serializer = CollectionsPreferencesSerializer,
    )

    fun getCollectionsStore(): DataStore<CollectionsPreferences> = context.collectionsStoreFile
}