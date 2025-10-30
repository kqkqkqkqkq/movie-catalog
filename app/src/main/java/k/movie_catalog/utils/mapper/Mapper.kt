package k.movie_catalog.utils.mapper

import k.movie_catalog.api.schemas.AuthResponseDto
import k.movie_catalog.api.schemas.GenderDto
import k.movie_catalog.api.schemas.LoginCredentialDto
import k.movie_catalog.api.schemas.ProfileDto
import k.movie_catalog.api.schemas.UserRegisterDto
import k.movie_catalog.data.collections.models.CollectionPreferences
import k.movie_catalog.data.collections.models.MoviePreferences
import k.movie_catalog.repositories.models.Auth
import k.movie_catalog.repositories.models.Collection
import k.movie_catalog.repositories.models.CollectionMovie
import k.movie_catalog.repositories.models.Gender
import k.movie_catalog.repositories.models.LoginCredential
import k.movie_catalog.repositories.models.Profile
import k.movie_catalog.repositories.models.UserRegister

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
    movieId = this.movieId,
)