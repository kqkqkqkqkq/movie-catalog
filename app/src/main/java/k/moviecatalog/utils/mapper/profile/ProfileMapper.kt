package k.moviecatalog.utils.mapper.profile

import k.moviecatalog.api.schemas.ProfileDto
import k.moviecatalog.repositories.models.Profile
import k.moviecatalog.repositories.models.UserShort
import k.moviecatalog.utils.mapper.gender.genderMapper
import k.moviecatalog.utils.mapper.gender.genderMapperDto

fun ProfileDto.toProfile() = Profile(
    id = id,
    nickName = nickName,
    email = email,
    avatarLink = avatarLink,
    name = name,
    birthDate = birthDate,
    gender = genderMapperDto(gender),
)

fun Profile.toProfileDto() = ProfileDto(
    id = id,
    nickName = nickName,
    email = email,
    avatarLink = avatarLink,
    name = name,
    birthDate = birthDate,
    gender = genderMapper(gender),
)

fun Profile.toUserShort() = UserShort(
    userId = id,
    nickName = nickName,
    avatar = avatarLink,
)