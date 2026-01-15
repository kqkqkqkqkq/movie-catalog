package k.moviecatalog.repositories.auth

import k.moviecatalog.api.routes.AuthApi
import k.moviecatalog.api.utils.handleApiCall
import k.moviecatalog.repositories.models.LoginCredential
import k.moviecatalog.repositories.models.UserRegister
import k.moviecatalog.utils.mapper.auth.toAuth
import k.moviecatalog.utils.mapper.profile.toProfile
import k.moviecatalog.utils.mapper.user.toLoginCredentialDto
import k.moviecatalog.utils.mapper.user.toUserRegisterDto

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