package k.movie_catalog.repositories.auth

import k.movie_catalog.api.routes.AuthApi
import k.movie_catalog.api.utils.handleApiCall
import k.movie_catalog.repositories.models.LoginCredential
import k.movie_catalog.repositories.models.UserRegister
import k.movie_catalog.utils.mapper.toAuth
import k.movie_catalog.utils.mapper.toLoginCredentialDto
import k.movie_catalog.utils.mapper.toProfile
import k.movie_catalog.utils.mapper.toUserRegisterDto

class AuthRepositoryImpl(
    private val authApi: AuthApi,
) : AuthRepository {

    override suspend fun register(userRegister: UserRegister) = handleApiCall {
        authApi.register(userRegister.toUserRegisterDto()).toAuth()
    }


    override suspend fun login(loginCredential: LoginCredential) = handleApiCall {
        authApi.login(loginCredential.toLoginCredentialDto()).toAuth()
    }

    override suspend fun logout() {
        authApi.logout()
    }

    override suspend fun getProfile() = handleApiCall {
        authApi.getProfile().toProfile()
    }
}