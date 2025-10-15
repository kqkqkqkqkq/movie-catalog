package k.movie_catalog.di

import com.github.terrakok.cicerone.Router
import k.movie_catalog.api.api.AuthApi

interface IAppComponent {
    val authApi: AuthApi
    val router: Router
}