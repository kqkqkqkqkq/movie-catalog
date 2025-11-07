package k.movie_catalog.repositories.favourites

import k.movie_catalog.api.routes.FavouritesApi
import k.movie_catalog.api.utils.handleApiCall
import k.movie_catalog.utils.mapper.toMovieElement
import java.util.UUID

class FavouritesRepositoryImpl(
    private val favouritesApi: FavouritesApi,
) : FavouritesRepository {

    override suspend fun getFavourites() = handleApiCall {
        favouritesApi.getFavourites().movies.map { it.toMovieElement() }
    }

    override suspend fun addFavourite(id: UUID) {
        favouritesApi.addFavourite(id)
    }

    override suspend fun deleteFavourite(id: UUID) {
        favouritesApi.deleteFavourite(id)
    }
}