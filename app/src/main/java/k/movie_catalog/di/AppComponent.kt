package k.movie_catalog.di

import android.content.Context
import k.movie_catalog.api.RetrofitClient
import k.movie_catalog.api.config.RetrofitConfig
import k.movie_catalog.api.routes.AuthApi
import k.movie_catalog.api.routes.Routes
import k.movie_catalog.repositories.AuthRepository
import k.movie_catalog.repositories.IAuthRepository

class AppComponent(
    private val context: Context,
) : IAppComponent {
    private val retrofitClient: RetrofitClient by lazy {
        val retrofitConfig = RetrofitConfig()
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
}