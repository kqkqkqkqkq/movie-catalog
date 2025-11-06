package k.movie_catalog.repositories.movie

import k.movie_catalog.repositories.models.MovieDetails
import k.movie_catalog.repositories.models.MoviesPagedList
import java.util.UUID

interface MoviesRepository {
    suspend fun getMovie(page: Int = 1): Result<MoviesPagedList>
    suspend fun getMovieDetails(id: UUID): Result<MovieDetails>
}