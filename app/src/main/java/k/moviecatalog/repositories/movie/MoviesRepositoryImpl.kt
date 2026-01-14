package k.moviecatalog.repositories.movie

import k.moviecatalog.api.routes.MovieApi
import k.moviecatalog.api.utils.handleApiCall
import k.moviecatalog.utils.mapper.toMovieDetails
import k.moviecatalog.utils.mapper.toMoviesPagedList
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