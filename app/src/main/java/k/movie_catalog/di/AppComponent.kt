package k.movie_catalog.di

import android.content.Context
import k.movie_catalog.api.RetrofitClient
import k.movie_catalog.api.config.RetrofitConfig
import k.movie_catalog.api.routes.AuthApi
import k.movie_catalog.api.routes.Routes
import k.movie_catalog.data.getDataStore
import k.movie_catalog.repositories.auth.AuthRepository
import k.movie_catalog.repositories.auth.IAuthRepository
import k.movie_catalog.repositories.auth.ITokenRepository
import k.movie_catalog.repositories.auth.TokenRepository

class AppComponent(
    private val context: Context,
) : IAppComponent {
    private val datastore by lazy { getDataStore(context) }
    private val retrofitConfig by lazy { RetrofitConfig() }
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
    override val authRepository: IAuthRepository by lazy {
        AuthRepository(authApi)
    }
    override val tokenRepository: ITokenRepository by lazy {
        TokenRepository(datastore)
    }
}