package k.movie_catalog.repositories.auth

import k.movie_catalog.repositories.models.Auth
import k.movie_catalog.repositories.models.LoginCredential
import k.movie_catalog.repositories.models.Profile
import k.movie_catalog.repositories.models.UserRegister

interface IAuthRepository {
    suspend fun register(userRegister: UserRegister): Result<Auth>
    suspend fun login(loginCredential: LoginCredential): Result<Auth>
    suspend fun logout()
    suspend fun getProfile(token: String): Result<Profile>
}