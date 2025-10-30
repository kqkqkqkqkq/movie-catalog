package k.movie_catalog.repositories.collections

import k.movie_catalog.repositories.models.Collection
import kotlinx.coroutines.flow.Flow

interface ICollectionsRepository {
    val collections: Flow<List<Collection>?>

    suspend fun getCollections(): List<Collection>?
    suspend fun createCollection(collection: Collection)
    suspend fun removeCollection(collection: Collection)
    suspend fun updateCollection(collectionName: String, collection: Collection)
}