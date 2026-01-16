package k.moviecatalog.repositories.collections

import androidx.datastore.core.DataStore
import k.moviecatalog.data.collections.models.CollectionsPreferences
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.CollectionMovie
import k.moviecatalog.utils.mapper.collection.toCollection
import k.moviecatalog.utils.mapper.collection.toCollectionPreferences
import k.moviecatalog.utils.mapper.collection.toMoviePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CollectionsRepositoryImpl(
    private val collectionsStore: DataStore<CollectionsPreferences>,
) : CollectionsRepository {

    override val collections: Flow<List<Collection>?> =
        collectionsStore.data.map { collections -> collections.collections?.map { it.toCollection() } }

    override suspend fun getCollections(): Result<List<Collection>?> = runCatching {
        collectionsStore.data.first().collections?.map { it.toCollection() }
    }

    override suspend fun createCollection(collection: Collection) {
        collectionsStore.updateData { currentPreferences ->
            val currentCollections = currentPreferences.collections?.toMutableList() ?: mutableListOf()
            val index = currentCollections.indexOfFirst { it.title == collection.title }
            require(index == 1) { "Collection already exists" }

            currentCollections.add(collection.toCollectionPreferences())
            currentPreferences.copy(collections = currentCollections)
        }
    }

    override suspend fun removeCollection(collection: Collection) {
        collectionsStore.updateData { currentPreferences ->
            val currentCollections =
                currentPreferences.collections?.toMutableList() ?: mutableListOf()
            currentCollections.removeIf { it.title == collection.title }
            currentPreferences.copy(collections = currentCollections)
        }
    }

    override suspend fun addMovieToCollection(
        collection: Collection,
        movie: CollectionMovie,
    ) {
        collectionsStore.updateData { currentPreferences ->
            val currentCollections =
                currentPreferences.collections?.toMutableList() ?: mutableListOf()
            val index = currentCollections.indexOfFirst { it.title == collection.title }
            if (index != -1) {
                val currentMovies =
                    currentCollections[index].movies?.toMutableList() ?: mutableListOf()
                val exists = currentMovies.any { it.movieId == movie.movieId }
                if (!exists) {
                    currentMovies.add(movie.toMoviePreferences())
                }
                currentCollections[index] = currentCollections[index].copy(movies = currentMovies)
            }
            currentPreferences.copy(collections = currentCollections)
        }
    }

    override suspend fun removeMovieFromCollection(
        collection: Collection,
        movie: CollectionMovie,
    ) {
        collectionsStore.updateData { currentPreferences ->
            val currentCollections =
                currentPreferences.collections?.toMutableList() ?: mutableListOf()
            val index = currentCollections.indexOfFirst { it.title == collection.title }

            if (index != -1) {
                val updatedMovies = currentCollections[index].movies
                    ?.filterNot { it.movieId == movie.movieId }
                currentCollections[index] = currentCollections[index].copy(movies = updatedMovies)
            }
            currentPreferences.copy(collections = currentCollections)
        }
    }

    override suspend fun updateCollection(collectionName: String, collection: Collection) {
        collectionsStore.updateData { currentPreferences ->
            val currentCollections =
                currentPreferences.collections?.toMutableList() ?: mutableListOf()
            val index = currentCollections.indexOfFirst { it.title == collectionName }
            if (index != -1) {
                val old = currentCollections[index]
                val updated = old.copy(
                    title = collection.title,
                    icon = collection.icon ?: old.icon,
                    movies = old.movies,
                )
                currentCollections[index] = updated
            }
            currentPreferences.copy(collections = currentCollections)
        }
    }
}