package k.movie_catalog.api.routes

import k.movie_catalog.api.schemas.AuthResponseDto
import k.movie_catalog.api.schemas.LoginCredentialDto
import k.movie_catalog.api.schemas.ProfileDto
import k.movie_catalog.api.schemas.UserRegisterDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST(Routes.REGISTER)
    suspend fun register(
        @Body userRegister: UserRegisterDto,
    ): AuthResponseDto

    @POST(Routes.LOGIN)
    suspend fun login(
        @Body userLogin: LoginCredentialDto,
    ): AuthResponseDto

    @POST(Routes.LOGOUT)
    suspend fun logout()

    @GET(Routes.PROFILE)
    suspend fun getProfile(): ProfileDto
}
