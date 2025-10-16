package k.movie_catalog.di

import k.movie_catalog.api.RetrofitClient
import k.movie_catalog.api.routes.AuthApi
import k.movie_catalog.repositories.IAuthRepository

interface IAppComponent {
    val authApi: AuthApi
    val authRepository: IAuthRepository
}