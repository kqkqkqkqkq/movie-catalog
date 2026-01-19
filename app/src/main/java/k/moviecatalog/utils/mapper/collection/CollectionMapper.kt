package k.moviecatalog.utils.mapper.collection

import k.moviecatalog.data.collections.models.CollectionPreferences
import k.moviecatalog.data.collections.models.MoviePreferences
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.CollectionMovie

fun MoviePreferences.toCollectionMovie() = CollectionMovie(
    title = title,
    description = description,
    posterUrl = posterUrl,
    movieId = movieId,
)

fun CollectionMovie.toMoviePreferences() = MoviePreferences(
    title = title,
    description = description,
    posterUrl = posterUrl,
    movieId = movieId,
)

fun CollectionPreferences.toCollection() = Collection(
    icon = icon,
    title = title.orEmpty(),
    movies = movies?.map { it.toCollectionMovie() }
)

fun Collection.toCollectionPreferences() = CollectionPreferences(
    icon = icon,
    title = title,
    movies = movies?.map { it.toMoviePreferences() }
)
