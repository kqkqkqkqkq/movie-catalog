package k.movie_catalog.api

import retrofit2.http.GET


/**
 * [API Documentation](https://react-midterm.kreosoft.space/swagger/index.html)
 */
interface MovieApi {

    @GET
    suspend fun getMovies()
}
