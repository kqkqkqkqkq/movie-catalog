package k.moviecatalog.utils.mapper.profile

import k.moviecatalog.api.schemas.ProfileDto
import k.moviecatalog.repositories.models.Profile
import k.moviecatalog.utils.mapper.gender.genderMapper
import k.moviecatalog.utils.mapper.gender.genderMapperDto

fun ProfileDto.toProfile() = Profile(
    id = this.id,
    nickName = this.nickName,
    email = this.email,
    avatarLink = this.avatarLink,
    name = this.name,
    birthDate = this.birthDate,
    gender = genderMapperDto(this.gender),
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