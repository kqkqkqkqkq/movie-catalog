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
    username = userName,
    name = name,
    password = password,
    email = email,
    birthDate = birthDate,
    gender = genderMapperDto(gender),
)

fun LoginCredentialDto.toLoginCredential() = LoginCredential(
    username = userName.orEmpty(),
    password = password.orEmpty(),
)

fun UserRegister.toUserRegisterDto() = UserRegisterDto(
    userName = username,
    name = name,
    password = password,
    email = email,
    birthDate = birthDate,
    gender = genderMapper(gender),
)

fun LoginCredential.toLoginCredentialDto() = LoginCredentialDto(
    userName = username,
    password = password,
)

fun UserShortDto.toUserShort() = UserShort(
    userId = userId,
    nickName = nickName,
    avatar = avatar,
)

fun UserShort.toUserShortDto() = UserShortDto(
    userId = userId,
    nickName = nickName,
    avatar = avatar,
)