package k.moviecatalog.utils.mapper.auth

import k.moviecatalog.api.schemas.AuthResponseDto
import k.moviecatalog.repositories.models.Auth

fun AuthResponseDto.toAuth() = Auth(
    token = token,
)

fun Auth.toAuthDto() = AuthResponseDto(
    token = token,
)
