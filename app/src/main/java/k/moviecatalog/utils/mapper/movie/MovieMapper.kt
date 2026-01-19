package k.moviecatalog.utils.mapper.movie

import k.moviecatalog.api.schemas.MovieDetailsDto
import k.moviecatalog.api.schemas.MovieElementDto
import k.moviecatalog.api.schemas.MoviesPagedListDto
import k.moviecatalog.api.schemas.PageInfoDto
import k.moviecatalog.api.schemas.ReviewShortDto
import k.moviecatalog.repositories.models.CollectionMovie
import k.moviecatalog.repositories.models.MovieDetails
import k.moviecatalog.repositories.models.MovieElement
import k.moviecatalog.repositories.models.MoviesPagedList
import k.moviecatalog.repositories.models.PageInfo
import k.moviecatalog.repositories.models.ReviewShort
import k.moviecatalog.utils.mapper.genre.toGenre
import k.moviecatalog.utils.mapper.genre.toGenreDto
import k.moviecatalog.utils.mapper.review.toReview
import k.moviecatalog.utils.mapper.review.toReviewDto
import k.moviecatalog.utils.mapper.review.toReviewShort
import k.moviecatalog.utils.mapper.review.toReviewShortDto

fun MoviesPagedListDto.toMoviesPagedList() = MoviesPagedList(
    movies = movies.map { it.toMovieElement() },
    pageInfo = pageInfo.toPageInfo(),
)

fun PageInfoDto.toPageInfo() = PageInfo(
    pageSize = pageSize,
    pageCount = pageCount,
    currentPage = currentPage,
)

fun MovieElementDto.toMovieElement() = MovieElement(
    id = id,
    name = name,
    poster = poster,
    year = year,
    country = country,
    genres = genres.map { it.toGenre() },
    reviews = reviews.map { it.toReviewShort() }
)

fun ReviewShortDto.toReviewShort() = ReviewShort(
    id = id,
    rating = rating,
)

fun MovieDetailsDto.toMovieDetails() = MovieDetails(
    id = id,
    name = name,
    poster = poster,
    year = year,
    country = country,
    genres = genres.map { it.toGenre() },
    reviews = reviews.map { it.toReview() },
    time = time,
    tagline = tagline,
    description = description,
    director = director,
    budget = budget,
    fees = fees,
    ageLimit = ageLimit,
)

fun MoviesPagedList.toMoviesPagedListDto() = MoviesPagedListDto(
    movies = movies.map { it.toMovieElementDto() },
    pageInfo = pageInfo.toPageInfoDto(),
)

fun PageInfo.toPageInfoDto() = PageInfoDto(
    pageSize = pageSize,
    pageCount = pageCount,
    currentPage = currentPage,
)

fun MovieElement.toMovieElementDto() = MovieElementDto(
    id = id,
    name = name,
    poster = poster,
    year = year,
    country = country,
    genres = genres.map { it.toGenreDto() },
    reviews = reviews.map { it.toReviewShortDto() },
)

fun MovieElement.toCollectionMovie() = CollectionMovie(
    title = name,
    description = name,
    posterUrl = poster,
    movieId = id,
)

fun MovieDetails.toMovieDetailsDto() = MovieDetailsDto(
    id = id,
    name = name,
    poster = poster,
    year = year,
    country = country,
    genres = genres.map { it.toGenreDto() },
    reviews = reviews.map { it.toReviewDto() },
    time = time,
    tagline = tagline,
    description = description,
    director = director,
    budget = budget,
    fees = fees,
    ageLimit = ageLimit
)

fun MovieDetails.toCollectionMovie() = CollectionMovie(
    title = name,
    posterUrl = poster,
    description = description,
    movieId = id,
)

fun MovieDetails.toMovieElement() = MovieElement(
    id = id,
    name = name,
    poster = poster,
    year = year,
    country = country,
    genres = genres,
    reviews = reviews.map { it.toReviewShort() },
)