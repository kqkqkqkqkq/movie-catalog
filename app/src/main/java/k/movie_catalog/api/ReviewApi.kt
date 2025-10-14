package k.movie_catalog.api

import k.movie_catalog.api.routes.Routes
import k.movie_catalog.api.schemas.ReviewModifyDto
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface ReviewApi {

    @POST(Routes.ADD_REVIEW)
    suspend fun addReview(
        @Path("movieId") movieId: UUID,
    ): Response<ReviewModifyDto>

    @PUT(Routes.UPDATE_REVIEW)
    suspend fun updateReview(
        @Path("movieId") movieId: UUID,
        @Path("id") id: UUID,
    ): Response<ReviewModifyDto>

    @PUT(Routes.DELETE_REVIEW)
    suspend fun deleteReview(
        @Path("movieId") movieId: UUID,
        @Path("id") id: UUID,
    )
}