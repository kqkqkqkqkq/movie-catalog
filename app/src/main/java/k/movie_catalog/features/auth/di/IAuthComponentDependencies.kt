package k.movie_catalog.features.auth.di

import com.github.terrakok.cicerone.Router
import k.movie_catalog.api.api.AuthApi

interface IAuthComponentDependencies {
    val authApi: AuthApi
    val router: Router
}