package k.movie_catalog.repositories.movie

import k.movie_catalog.api.routes.MovieApi
import k.movie_catalog.api.utils.handleApiCall
import k.movie_catalog.utils.mapper.toMovieDetails
import k.movie_catalog.utils.mapper.toMoviesPagedList
import java.util.UUID

class MoviesRepositoryImpl(
    private val movieApi: MovieApi,
) : MoviesRepository {

    override suspend fun getMovie(page: Int) = handleApiCall {
        movieApi.getMovies(page).toMoviesPagedList()
    }


    override suspend fun getMovieDetails(id: UUID) = handleApiCall {
        movieApi.getMovieDetails(id).toMovieDetails()
    }
}