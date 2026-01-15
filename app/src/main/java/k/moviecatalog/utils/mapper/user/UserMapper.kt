package k.moviecatalog.utils.mapper.user

import k.moviecatalog.api.schemas.LoginCredentialDto
import k.moviecatalog.api.schemas.UserRegisterDto
import k.moviecatalog.api.schemas.UserShortDto
import k.moviecatalog.repositories.models.LoginCredential
import k.moviecatalog.repositories.models.UserRegister
import k.moviecatalog.repositories.models.UserShort
import k.moviecatalog.utils.mapper.gender.genderMapper
import k.moviecatalog.utils.mapper.gender.genderMapperDto

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

fun UserShortDto.toUserShort() = UserShort(
    userId = this.userId,
    nickName = this.nickName,
    avatar = this.avatar,
)

fun UserShort.toUserShortDto() = UserShortDto(
    userId = this.userId,
    nickName = this.nickName,
    avatar = this.avatar,
)