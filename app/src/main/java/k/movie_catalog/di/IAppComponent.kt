package k.movie_catalog.di

import k.movie_catalog.api.routes.AuthApi
import k.movie_catalog.api.routes.MovieApi
import k.movie_catalog.repositories.auth.IAuthRepository
import k.movie_catalog.repositories.collections.ICollectionsRepository
import k.movie_catalog.repositories.token.ITokenRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider

interface IAppComponent {
    val dispatcherProvider: DispatcherProvider
    val authApi: AuthApi
    val movieApi: MovieApi
    val authRepository: IAuthRepository
    val tokenRepository: ITokenRepository
    val collectionsRepository: ICollectionsRepository
}