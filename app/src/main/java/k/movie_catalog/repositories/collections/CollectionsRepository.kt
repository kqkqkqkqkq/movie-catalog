package k.movie_catalog.repositories.collections

import k.movie_catalog.repositories.models.Collection
import k.movie_catalog.repositories.models.CollectionMovie
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {
    val collections: Flow<List<Collection>?>

    suspend fun getCollections(): List<Collection>?
    suspend fun createCollection(collection: Collection)
    suspend fun removeCollection(collection: Collection)
    suspend fun addMovieToCollection(collection: Collection, movie: CollectionMovie)
    suspend fun removeMovieFromCollection(collection: Collection, movie: CollectionMovie)
    suspend fun updateCollection(collectionName: String, collection: Collection)
}