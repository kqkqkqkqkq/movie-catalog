package k.moviecatalog.repositories.movie

import k.moviecatalog.repositories.models.MovieDetails
import k.moviecatalog.repositories.models.MoviesPagedList
import java.util.UUID

interface MoviesRepository {
    suspend fun getMovie(page: Int = 1): Result<MoviesPagedList>
    suspend fun getMovieDetails(id: UUID): Result<MovieDetails>
}