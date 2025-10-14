package k.movie_catalog.api

import k.movie_catalog.api.routes.Routes
import k.movie_catalog.api.schemas.AuthResponseDto
import k.movie_catalog.api.schemas.LoginCredentialDto
import k.movie_catalog.api.schemas.ProfileDto
import k.movie_catalog.api.schemas.UserRegisterDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApi {

    @POST(Routes.REGISTER)
    suspend fun register(
        @Body userRegister: UserRegisterDto,
    ): Response<AuthResponseDto>

    @POST(Routes.LOGIN)
    suspend fun login(
        @Body userLogin: LoginCredentialDto,
    ): Response<AuthResponseDto>

    @POST(Routes.LOGOUT)
    suspend fun logout()

    @GET(Routes.PROFILE)
    suspend fun getProfile(): Response<ProfileDto>

    @PUT(Routes.UPDATE_PROFILE)
    suspend fun updateProfile(
        @Body profileUpdate: ProfileDto
    )
}
