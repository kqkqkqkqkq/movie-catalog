package k.moviecatalog.utils.mapper.gender

import k.moviecatalog.api.schemas.GenderDto
import k.moviecatalog.repositories.models.Gender

fun genderMapperDto(genderDto: GenderDto?) = when (genderDto) {
    GenderDto.MALE -> Gender.MALE
    GenderDto.FEMALE -> Gender.FEMALE
    null -> Gender.UNKNOW
}

fun genderMapper(gender: Gender) = when (gender) {
    Gender.MALE -> GenderDto.MALE
    Gender.FEMALE -> GenderDto.FEMALE
    Gender.UNKNOW -> null
}
