package k.movie_catalog.api.routes

import k.movie_catalog.api.schemas.MovieDetailsDto
import k.movie_catalog.api.schemas.MoviesPagedListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID


/**
 * [API Documentation](https://react-midterm.kreosoft.space/swagger/index.html)
 */
interface MovieApi {

    @GET(Routes.MOVIES)
    suspend fun getMovies(
        @Path("page") page: Int = 1,
    ): MoviesPagedListDto

    @GET(Routes.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path("id") id: UUID,
    ): MovieDetailsDto
}
