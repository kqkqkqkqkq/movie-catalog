package k.moviecatalog.repositories.favourites

import k.moviecatalog.repositories.models.MovieElement
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FavouritesRepository {
    fun getFavouritesFlow(): Flow<List<MovieElement>>
    suspend fun getFavourites(): Result<List<MovieElement>>
    suspend fun addFavourite(movie: MovieElement): Result<Unit>
    suspend fun deleteFavourite(id: UUID): Result<Unit>
}