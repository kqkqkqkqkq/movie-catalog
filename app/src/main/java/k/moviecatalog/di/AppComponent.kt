package k.moviecatalog.di

import k.moviecatalog.repositories.auth.AuthRepository
import k.moviecatalog.repositories.collections.CollectionsRepository
import k.moviecatalog.repositories.favourites.FavouritesRepository
import k.moviecatalog.repositories.movie.MoviesRepository
import k.moviecatalog.repositories.token.TokenRepository
import k.moviecatalog.common.dispatcher.DispatcherProvider
import k.moviecatalog.repositories.review.ReviewRepository

interface AppComponent {
    val authRepository: AuthRepository
    val tokenRepository: TokenRepository
    val moviesRepository: MoviesRepository
    val favouritesRepository: FavouritesRepository
    val collectionsRepository: CollectionsRepository
    val reviewRepository: ReviewRepository
    val dispatcherProvider: DispatcherProvider
}
