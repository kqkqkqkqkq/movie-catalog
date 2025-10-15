package k.movie_catalog.di

import com.github.terrakok.cicerone.Router
import k.movie_catalog.api.RetrofitClient
import k.movie_catalog.api.api.AuthApi

fun AppComponent(
    router: Router,
): IAppComponent = object : IAppComponent {
    override val authApi: AuthApi
        get() = RetrofitClient.authApi
    override val router: Router
        get() = router
}