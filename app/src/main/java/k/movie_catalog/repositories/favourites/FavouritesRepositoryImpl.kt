package k.movie_catalog.repositories.favourites

import k.movie_catalog.api.routes.FavouritesApi
import k.movie_catalog.api.utils.handleApiCall
import k.movie_catalog.repositories.models.MovieElement
import k.movie_catalog.utils.mapper.toMovieElement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FavouritesRepositoryImpl(
    private val favouritesApi: FavouritesApi,
) : FavouritesRepository {

    private val favouritesFlow = MutableStateFlow<List<MovieElement>>(emptyList())

    override fun getFavouritesFlow(): Flow<List<MovieElement>> = favouritesFlow

    override suspend fun getFavourites() = handleApiCall {
        val favourites = favouritesApi.getFavourites().movies.map { it.toMovieElement() }
        favouritesFlow.value = favourites
        favourites
    }

    override suspend fun addFavourite(movie: MovieElement) = handleApiCall {
        favouritesApi.addFavourite(movie.id)
        val updatedFavourites = favouritesApi.getFavourites().movies.map { it.toMovieElement() }
        favouritesFlow.value = updatedFavourites
    }

    override suspend fun deleteFavourite(movie: MovieElement) = handleApiCall {
        favouritesApi.deleteFavourite(movie.id)
        val updatedFavourites = favouritesApi.getFavourites().movies.map { it.toMovieElement() }
        favouritesFlow.value = updatedFavourites
    }
}