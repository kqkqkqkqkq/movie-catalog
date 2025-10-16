package k.movie_catalog.repositories.auth

import k.movie_catalog.api.routes.AuthApi
import k.movie_catalog.repositories.models.Auth
import k.movie_catalog.repositories.models.LoginCredential
import k.movie_catalog.repositories.models.Profile
import k.movie_catalog.repositories.models.UserRegister
import k.movie_catalog.api.utils.handleApiCall
import k.movie_catalog.utils.mapper.toAuth
import k.movie_catalog.utils.mapper.toLoginCredentialDto
import k.movie_catalog.utils.mapper.toProfile
import k.movie_catalog.utils.mapper.toProfileDto
import k.movie_catalog.utils.mapper.toUserRegisterDto

class AuthRepository(
    private val authApi: AuthApi,
) : IAuthRepository {
    override suspend fun register(userRegister: UserRegister): Result<Auth> {
        return handleApiCall {
            authApi.register(userRegister.toUserRegisterDto())
        }.map {
            it.toAuth()
        }
    }

    override suspend fun login(loginCredential: LoginCredential): Result<Auth> {
        return handleApiCall {
            authApi.login(loginCredential.toLoginCredentialDto())
        }.map {
            it.toAuth()
        }
    }

    override suspend fun logout() {
        authApi.logout()
    }

    override suspend fun getProfile(): Result<Profile> {
        return handleApiCall {
            authApi.getProfile()
        }.map {
            it.toProfile()
        }
    }

    override suspend fun updateProfile(profile: Profile) {
        authApi.updateProfile(profile.toProfileDto())
    }
}