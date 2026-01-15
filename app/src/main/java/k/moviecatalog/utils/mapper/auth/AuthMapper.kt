package k.moviecatalog.utils.mapper.auth

import k.moviecatalog.api.schemas.AuthResponseDto
import k.moviecatalog.repositories.models.Auth

fun AuthResponseDto.toAuth() = Auth(
    token = this.token,
)

fun Auth.toAuthDto() = AuthResponseDto(
    token = this.token,
)
