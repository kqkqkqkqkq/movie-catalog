package k.movie_catalog.repositories.favourites

import k.movie_catalog.repositories.models.MovieElement
import java.util.UUID

interface FavouritesRepository {
    suspend fun getFavourites(): Result<List<MovieElement>>
    suspend fun addFavourite(id: UUID)
    suspend fun deleteFavourite(id: UUID)
}