package k.movie_catalog.api.routes

import k.movie_catalog.api.schemas.MoviesListDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface FavouritesApi {

    @GET(Routes.FAVOURITES)
    suspend fun getFavourites(
        @Header("Bearer") token: String,
    ): Response<MoviesListDto>

    @POST(Routes.ADD_FAVOURITE)
    suspend fun addFavourite(
        @Header("Bearer") token: String,
        @Path("id") id: UUID,
    )

    @DELETE(Routes.DELETE_FAVOURITE)
    suspend fun deleteFavourite(
        @Header("Bearer") token: String,
        @Path("id") id: UUID,
    )
}