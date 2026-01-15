package k.moviecatalog.utils.mapper.collection

import k.moviecatalog.data.collections.models.CollectionPreferences
import k.moviecatalog.data.collections.models.MoviePreferences
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.CollectionMovie

fun MoviePreferences.toCollectionMovie() = CollectionMovie(
    title = this.title,
    description = this.description,
    posterUrl = this.posterUrl,
    movieId = this.movieId,
)

fun CollectionMovie.toMoviePreferences() = MoviePreferences(
    title = this.title,
    description = this.description,
    posterUrl = this.posterUrl,
    movieId = this.movieId,
)

fun CollectionPreferences.toCollection() = k.moviecatalog.repositories.models.Collection(
    icon = this.icon,
    title = this.title ?: "",
    movies = this.movies?.map { it.toCollectionMovie() }
)

fun Collection.toCollectionPreferences() = CollectionPreferences(
    icon = this.icon,
    title = this.title,
    movies = this.movies?.map { it.toMoviePreferences() }
)
