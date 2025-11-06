package k.movie_catalog.repositories.collections

import androidx.datastore.core.DataStore
import k.movie_catalog.data.collections.models.CollectionsPreferences
import k.movie_catalog.repositories.models.Collection
import k.movie_catalog.utils.mapper.toCollection
import k.movie_catalog.utils.mapper.toCollectionPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CollectionsRepositoryImpl(
    private val collectionsStore: DataStore<CollectionsPreferences>,
) : CollectionsRepository {

    override val collections: Flow<List<Collection>?> =
        collectionsStore.data.map { collections -> collections.collections?.map { it.toCollection() } }

    override suspend fun getCollections(): List<Collection>? {
        return collectionsStore.data.first()
            .collections
            ?.map { it.toCollection() }
    }

    override suspend fun createCollection(collection: Collection) {
        collectionsStore.updateData { currentPreferences ->
            val currentCollections =
                currentPreferences.collections?.toMutableList() ?: mutableListOf()
            val index = currentCollections.indexOfFirst { it.title == collection.title }
            if (index == -1) {
                currentCollections.add(collection.toCollectionPreferences())
            }
            currentPreferences.copy(collections = currentCollections)
        }
    }

    override suspend fun removeCollection(collection: Collection) {
        println(collection)
        collectionsStore.updateData { currentPreferences ->
            val currentCollections =
                currentPreferences.collections?.toMutableList() ?: mutableListOf()
            currentCollections.removeIf { it.title == collection.title }
            currentPreferences.copy(collections = currentCollections)
        }
    }

    override suspend fun updateCollection(collectionName: String, collection: Collection) {
        collectionsStore.updateData { currentPreferences ->
            val currentCollections =
                currentPreferences.collections?.toMutableList() ?: mutableListOf()
            val index = currentCollections.indexOfFirst { it.title == collectionName }
            if (index != -1) {
                currentCollections[index] = collection.toCollectionPreferences()
            }
            currentPreferences.copy(collections = currentCollections)
        }
    }
}