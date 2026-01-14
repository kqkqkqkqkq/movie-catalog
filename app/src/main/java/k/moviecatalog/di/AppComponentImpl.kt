package k.moviecatalog.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import k.moviecatalog.api.RetrofitClient
import k.moviecatalog.api.config.RetrofitConfig
import k.moviecatalog.api.interceptors.AuthTokenInterceptor
import k.moviecatalog.api.interceptors.UnauthorizedInterceptor
import k.moviecatalog.api.routes.Routes
import k.moviecatalog.data.collections.CollectionsStore
import k.moviecatalog.data.token.TokenStore
import k.moviecatalog.repositories.auth.AuthRepositoryImpl
import k.moviecatalog.repositories.collections.CollectionsRepositoryImpl
import k.moviecatalog.repositories.favourites.FavouritesRepositoryImpl
import k.moviecatalog.repositories.movie.MoviesRepositoryImpl
import k.moviecatalog.repositories.token.TokenRepositoryImpl
import k.moviecatalog.utils.dispatcher.MovieCatalogDispatcherProvider

class AppComponentImpl(
    private val context: Context,
) : AppComponent {
    private val tokenStore by lazy { TokenStore(context) }
    private val collectionsStore by lazy { CollectionsStore(context) }
    private val authApi by lazy { retrofitClient.authApi }
    private val movieApi by lazy { retrofitClient.movieApi }
    private val favouritesApi by lazy { retrofitClient.favouritesApi }
    private val authTokenInterceptor by lazy { AuthTokenInterceptor(tokenRepository) }
    private val unauthorizedInterceptor by lazy { UnauthorizedInterceptor(tokenRepository) }
    private val retrofitConfig by lazy {
        RetrofitConfig(
            authTokenInterceptor = authTokenInterceptor,
            unauthorizedInterceptor = unauthorizedInterceptor,
        )
    }
    private val retrofitClient by lazy {
        RetrofitClient(
            baseUrl = Routes.BASE_URL + Routes.API_VERSION,
            okHttpClient = retrofitConfig.buildOkHttpClient(),
            json = retrofitConfig.buildJson(),
        )
    }

    override val authRepository by lazy { AuthRepositoryImpl(authApi) }
    override val tokenRepository by lazy { TokenRepositoryImpl(tokenStore.getTokenStore()) }
    override val moviesRepository by lazy { MoviesRepositoryImpl(movieApi) }
    override val favouritesRepository by lazy { FavouritesRepositoryImpl(favouritesApi) }
    override val collectionsRepository by lazy {
        CollectionsRepositoryImpl(collectionsStore.getCollectionsStore())
    }

    override val dispatcherProvider by lazy { MovieCatalogDispatcherProvider() }

    companion object {
        fun <VM : ViewModel> viewModelFactory(vm: () -> VM) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return vm() as T
            }
        }
    }
}