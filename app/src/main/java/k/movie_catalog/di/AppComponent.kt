package k.movie_catalog.di

import k.movie_catalog.api.interceptors.AuthTokenInterceptor
import k.movie_catalog.api.interceptors.UnauthorizedInterceptor
import k.movie_catalog.api.routes.AuthApi
import k.movie_catalog.api.routes.MovieApi
import k.movie_catalog.repositories.auth.AuthRepository
import k.movie_catalog.repositories.collections.CollectionsRepository
import k.movie_catalog.repositories.token.TokenRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider

interface AppComponent {
    val dispatcherProvider: DispatcherProvider
    val authApi: AuthApi
    val movieApi: MovieApi
    val authRepository: AuthRepository
    val tokenRepository: TokenRepository
    val collectionsRepository: CollectionsRepository
    val authTokenInterceptor: AuthTokenInterceptor
    val unauthorizedInterceptor: UnauthorizedInterceptor
}