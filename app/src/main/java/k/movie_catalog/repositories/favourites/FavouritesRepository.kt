package k.movie_catalog.repositories.favourites

import k.movie_catalog.repositories.models.MovieElement
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getFavouritesFlow(): Flow<List<MovieElement>>
    suspend fun getFavourites(): Result<List<MovieElement>>
    suspend fun addFavourite(movie: MovieElement): Result<Unit>
    suspend fun deleteFavourite(movie: MovieElement): Result<Unit>
}