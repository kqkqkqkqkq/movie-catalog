package k.movie_catalog.di

import android.content.Context
import k.movie_catalog.api.RetrofitClient
import k.movie_catalog.api.config.RetrofitConfig
import k.movie_catalog.api.interceptors.AuthTokenInterceptor
import k.movie_catalog.api.interceptors.UnauthorizedInterceptor
import k.movie_catalog.api.routes.AuthApi
import k.movie_catalog.api.routes.MovieApi
import k.movie_catalog.api.routes.Routes
import k.movie_catalog.data.collections.CollectionsStore
import k.movie_catalog.data.token.TokenStore
import k.movie_catalog.repositories.auth.AuthRepository
import k.movie_catalog.repositories.auth.IAuthRepository
import k.movie_catalog.repositories.collections.CollectionsRepository
import k.movie_catalog.repositories.collections.ICollectionsRepository
import k.movie_catalog.repositories.token.ITokenRepository
import k.movie_catalog.repositories.token.TokenRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import k.movie_catalog.utils.dispatcher.MovieCatalogDispatcherProvider

class AppComponent(
    private val context: Context,
) : IAppComponent {

    override val dispatcherProvider: DispatcherProvider by lazy { MovieCatalogDispatcherProvider() }
    private val tokenStore by lazy { TokenStore(context) }
    private val collectionsStore by lazy { CollectionsStore(context) }
    override val tokenRepository: ITokenRepository by lazy { TokenRepository(tokenStore.getTokenStore()) }
    override val collectionsRepository: ICollectionsRepository by lazy {
        CollectionsRepository(collectionsStore.getCollectionsStore())
    }
    override val authRepository: IAuthRepository by lazy {
        AuthRepository(authApi)
    }
    override val authTokenInterceptor by lazy { AuthTokenInterceptor(tokenRepository) }
    override val unauthorizedInterceptor by lazy { UnauthorizedInterceptor(tokenRepository) }
    private val retrofitConfig by lazy {
        RetrofitConfig(
            authTokenInterceptor = authTokenInterceptor,
            unauthorizedInterceptor = unauthorizedInterceptor,
        )
    }
    private val retrofitClient: RetrofitClient by lazy {
        RetrofitClient(
            baseUrl = Routes.BASE_URL + Routes.API_VERSION,
            okHttpClient = retrofitConfig.buildOkHttpClient(),
            json = retrofitConfig.buildJson(),
        )
    }
    override val authApi: AuthApi by lazy {
        retrofitClient.authApi
    }
    override val movieApi: MovieApi by lazy {
        retrofitClient.movieApi
    }
}