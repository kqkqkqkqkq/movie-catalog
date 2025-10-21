package k.movie_catalog.utils.mapper

import k.movie_catalog.api.schemas.AuthResponseDto
import k.movie_catalog.api.schemas.GenderDto
import k.movie_catalog.api.schemas.LoginCredentialDto
import k.movie_catalog.api.schemas.ProfileDto
import k.movie_catalog.api.schemas.UserRegisterDto
import k.movie_catalog.repositories.models.Auth
import k.movie_catalog.repositories.models.Gender
import k.movie_catalog.repositories.models.LoginCredential
import k.movie_catalog.repositories.models.Profile
import k.movie_catalog.repositories.models.UserRegister

fun AuthResponseDto.toAuth() = Auth(
    token = this.token,
)

fun UserRegisterDto.toUserRegister() = UserRegister(
    userName = this.userName,
    name = this.name,
    password = this.password,
    email = this.email,
    birthDate = this.birthDate,
    gender = genderMapperDto(this.genderDto),
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
    gender = genderMapperDto(this.genderDto),
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
    userName = this.userName,
    name = this.name,
    password = this.password,
    email = this.email,
    birthDate = this.birthDate,
    genderDto = genderMapper(this.gender),
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
    genderDto = genderMapper(this.gender),
)