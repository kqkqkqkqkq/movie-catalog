package k.movie_catalog.api.routes

import k.movie_catalog.api.schemas.MoviesListDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface FavouritesApi {

    @GET(Routes.FAVOURITES)
    suspend fun getFavourites(): MoviesListDto

    @POST(Routes.ADD_FAVOURITE)
    suspend fun addFavourite(
        @Path("id") id: UUID,
    )

    @DELETE(Routes.DELETE_FAVOURITE)
    suspend fun deleteFavourite(
        @Path("id") id: UUID,
    )
}