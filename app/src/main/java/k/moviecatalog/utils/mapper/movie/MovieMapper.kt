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
import k.moviecatalog.utils.mapper.gender.toGenre
import k.moviecatalog.utils.mapper.gender.toGenreDto
import k.moviecatalog.utils.mapper.review.toReview
import k.moviecatalog.utils.mapper.review.toReviewDto
import k.moviecatalog.utils.mapper.review.toReviewShort
import k.moviecatalog.utils.mapper.review.toReviewShortDto

fun MoviesPagedListDto.toMoviesPagedList() = MoviesPagedList(
    movies = this.movies.map { it.toMovieElement() },
    pageInfo = this.pageInfo.toPageInfo(),
)

fun PageInfoDto.toPageInfo() = PageInfo(
    pageSize = this.pageSize,
    pageCount = this.pageCount,
    currentPage = this.currentPage,
)

fun MovieElementDto.toMovieElement() = MovieElement(
    id = this.id,
    name = this.name,
    poster = this.poster,
    year = this.year,
    country = this.country,
    genres = this.genres.map { it.toGenre() },
    reviews = this.reviews.map { it.toReviewShort() }
)

fun ReviewShortDto.toReviewShort() = ReviewShort(
    id = this.id,
    rating = this.rating,
)

fun MovieDetailsDto.toMovieDetails() = MovieDetails(
    id = this.id,
    name = this.name,
    poster = this.poster,
    year = this.year,
    country = this.country,
    genres = this.genres.map { it.toGenre() },
    reviews = this.reviews.map { it.toReview() },
    time = this.time,
    tagline = this.tagline,
    description = this.description,
    director = this.director,
    budget = this.budget,
    fees = this.fees,
    ageLimit = this.ageLimit,
)

fun MoviesPagedList.toMoviesPagedListDto() = MoviesPagedListDto(
    movies = this.movies.map { it.toMovieElementDto() },
    pageInfo = this.pageInfo.toPageInfoDto(),
)

fun PageInfo.toPageInfoDto() = PageInfoDto(
    pageSize = this.pageSize,
    pageCount = this.pageCount,
    currentPage = this.currentPage,
)

fun MovieElement.toMovieElementDto() = MovieElementDto(
    id = this.id,
    name = this.name,
    poster = this.poster,
    year = this.year,
    country = this.country,
    genres = this.genres.map { it.toGenreDto() },
    reviews = this.reviews.map { it.toReviewShortDto() },
)

fun MovieElement.toCollectionMovie() = CollectionMovie(
    title = this.name,
    description = this.name,
    posterUrl = this.poster,
    movieId = this.id,
)

fun MovieDetails.toMovieDetailsDto() = MovieDetailsDto(
    id = this.id,
    name = this.name,
    poster = this.poster,
    year = this.year,
    country = this.country,
    genres = this.genres.map { it.toGenreDto() },
    reviews = this.reviews.map { it.toReviewDto() },
    time = this.time,
    tagline = this.tagline,
    description = this.description,
    director = this.director,
    budget = this.budget,
    fees = this.fees,
    ageLimit = this.ageLimit
)

fun MovieDetails.toCollectionMovie() = CollectionMovie(
    title = this.name,
    posterUrl = this.poster,
    description = this.description,
    movieId = this.id,
)

fun MovieDetails.toMovieElement() = MovieElement(
    id = this.id,
    name = this.name,
    poster = this.poster,
    year = this.year,
    country = this.country,
    genres = this.genres,
    reviews = this.reviews.map { it.toReviewShort() },
)