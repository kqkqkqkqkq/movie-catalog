package k.movie_catalog.di

import k.movie_catalog.api.routes.AuthApi
import k.movie_catalog.repositories.auth.IAuthRepository
import k.movie_catalog.repositories.auth.ITokenRepository

interface IAppComponent {
    val authApi: AuthApi
    val authRepository: IAuthRepository
    val tokenRepository: ITokenRepository
}