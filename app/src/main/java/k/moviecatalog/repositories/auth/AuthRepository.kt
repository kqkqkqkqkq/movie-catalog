package k.moviecatalog.repositories.auth

import k.moviecatalog.repositories.models.Auth
import k.moviecatalog.repositories.models.LoginCredential
import k.moviecatalog.repositories.models.Profile
import k.moviecatalog.repositories.models.UserRegister

interface AuthRepository {
    suspend fun register(userRegister: UserRegister): Result<Auth>
    suspend fun login(loginCredential: LoginCredential): Result<Auth>
    suspend fun logout()
    suspend fun getProfile(): Result<Profile>
}