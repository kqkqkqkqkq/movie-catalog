package k.moviecatalog.utils.mapper

import k.moviecatalog.api.schemas.AuthResponseDto
import k.moviecatalog.api.schemas.GenderDto
import k.moviecatalog.api.schemas.GenreDto
import k.moviecatalog.api.schemas.LoginCredentialDto
import k.moviecatalog.api.schemas.MovieDetailsDto
import k.moviecatalog.api.schemas.MovieElementDto
import k.moviecatalog.api.schemas.MoviesPagedListDto
import k.moviecatalog.api.schemas.PageInfoDto
import k.moviecatalog.api.schemas.ProfileDto
import k.moviecatalog.api.schemas.ReviewDto
import k.moviecatalog.api.schemas.ReviewShortDto
import k.moviecatalog.api.schemas.UserRegisterDto
import k.moviecatalog.api.schemas.UserShortDto
import k.moviecatalog.data.collections.models.CollectionPreferences
import k.moviecatalog.data.collections.models.MoviePreferences
import k.moviecatalog.repositories.models.Auth
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.CollectionMovie
import k.moviecatalog.repositories.models.Gender
import k.moviecatalog.repositories.models.Genre
import k.moviecatalog.repositories.models.LoginCredential
import k.moviecatalog.repositories.models.MovieDetails
import k.moviecatalog.repositories.models.MovieElement
import k.moviecatalog.repositories.models.MoviesPagedList
import k.moviecatalog.repositories.models.PageInfo
import k.moviecatalog.repositories.models.Profile
import k.moviecatalog.repositories.models.Review
import k.moviecatalog.repositories.models.ReviewShort
import k.moviecatalog.repositories.models.UserRegister
import k.moviecatalog.repositories.models.UserShort

fun AuthResponseDto.toAuth() = Auth(
    token = this.token,
)

fun UserRegisterDto.toUserRegister() = UserRegister(
    username = this.userName,
    name = this.name,
    password = this.password,
    email = this.email,
    birthDate = this.birthDate,
    gender = genderMapperDto(this.gender),
)

fun LoginCredentialDto.toLoginCredential() = LoginCredential(
    username = this.userName ?: "",
    password = this.password ?: "",
)

fun ProfileDto.toProfile() = Profile(
    id = this.id,
    nickName = this.nickName,
    email = this.email,
    avatarLink = this.avatarLink,
    name = this.name,
    birthDate = this.birthDate,
    gender = genderMapperDto(this.gender),
)

private fun genderMapperDto(genderDto: GenderDto?): Gender =
    when (genderDto) {
        GenderDto.MALE -> Gender.MALE
        GenderDto.FEMALE -> Gender.FEMALE
        null -> Gender.UNKNOW
    }

private fun genderMapper(gender: Gender): GenderDto? =
    when (gender) {
        Gender.MALE -> GenderDto.MALE
        Gender.FEMALE -> GenderDto.FEMALE
        Gender.UNKNOW -> null
    }

fun Auth.toAuthDto() = AuthResponseDto(
    token = this.token,
)

fun UserRegister.toUserRegisterDto() = UserRegisterDto(
    userName = this.username,
    name = this.name,
    password = this.password,
    email = this.email,
    birthDate = this.birthDate,
    gender = genderMapper(this.gender),
)

fun LoginCredential.toLoginCredentialDto() = LoginCredentialDto(
    userName = this.username,
    password = this.password,
)

fun Profile.toProfileDto() = ProfileDto(
    id = this.id,
    nickName = this.nickName,
    email = this.email,
    avatarLink = this.avatarLink,
    name = this.name,
    birthDate = this.birthDate,
    gender = genderMapper(this.gender),
)

fun CollectionPreferences.toCollection() = Collection(
    icon = this.icon,
    title = this.title ?: "",
    movies = this.movies?.map { it.toCollectionMovie() }
)

fun MoviePreferences.toCollectionMovie() = CollectionMovie(
    title = this.title,
    description = this.description,
    posterUrl = this.posterUrl,
    movieId = this.movieId,
)

fun Collection.toCollectionPreferences() = CollectionPreferences(
    icon = this.icon,
    title = this.title,
    movies = this.movies?.map { it.toMoviePreferences() }
)

fun CollectionMovie.toMoviePreferences() = MoviePreferences(
    title = this.title,
    description = this.description,
    posterUrl = this.posterUrl,
    movieId = this.movieId,
)

fun MoviesPagedListDto.toMoviesPagedList() = MoviesPagedList(
    movies = this.movies.map { it.toMovieElement() },
    pageInfo = this.pageInfo.toPageInfo()
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

fun GenreDto.toGenre() = Genre(
    id = this.id,
    name = this.name,
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

fun ReviewDto.toReview() = Review(
    id = this.id,
    rating = this.rating,
    reviewText = this.reviewText,
    isAnonymous = this.isAnonymous,
    createDateTime = this.createDateTime,
    author = this.author.toUserShort(),
)

fun UserShortDto.toUserShort() = UserShort(
    userId = this.userId,
    nickName = this.nickName,
    avatar = this.avatar,
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

fun ReviewShort.toReviewShortDto() = ReviewShortDto(
    id = this.id,
    rating = this.rating,
)

fun Genre.toGenreDto() = GenreDto(
    id = this.id,
    name = this.name,
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

fun Review.toReviewDto() = ReviewDto(
    id = this.id,
    rating = this.rating,
    reviewText = this.reviewText,
    isAnonymous = this.isAnonymous,
    createDateTime = this.createDateTime,
    author = this.author.toUserShortDto()
)

fun UserShort.toUserShortDto() = UserShortDto(
    userId = this.userId,
    nickName = this.nickName,
    avatar = this.avatar
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

fun Review.toReviewShort() = ReviewShort(
    id = this.id,
    rating = this.rating,
)
