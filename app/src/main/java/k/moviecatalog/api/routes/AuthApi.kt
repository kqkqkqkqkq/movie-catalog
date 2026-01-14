package k.moviecatalog.api.routes

import k.moviecatalog.api.schemas.AuthResponseDto
import k.moviecatalog.api.schemas.LoginCredentialDto
import k.moviecatalog.api.schemas.ProfileDto
import k.moviecatalog.api.schemas.UserRegisterDto
import retrofit2.http.Body
import retrofit2.http.GET
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
