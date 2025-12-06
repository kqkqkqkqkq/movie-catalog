package k.movie_catalog.di

import k.movie_catalog.repositories.auth.AuthRepository
import k.movie_catalog.repositories.collections.CollectionsRepository
import k.movie_catalog.repositories.favourites.FavouritesRepository
import k.movie_catalog.repositories.movie.MoviesRepository
import k.movie_catalog.repositories.token.TokenRepository
import k.movie_catalog.utils.dispatcher.DispatcherProvider

interface AppComponent {
    val authRepository: AuthRepository
    val tokenRepository: TokenRepository
    val moviesRepository: MoviesRepository
    val favouritesRepository: FavouritesRepository
    val collectionsRepository: CollectionsRepository
    val dispatcherProvider: DispatcherProvider
}
