package k.movie_catalog.api.schemas

import k.movie_catalog.utils.serializer.GenderDtoSerializer
import kotlinx.serialization.Serializable

@Serializable(with = GenderDtoSerializer::class)
enum class GenderDto {
    MALE,
    FEMALE;

    companion object {
        fun fromInt(value: Int): GenderDto {
            return when (value) {
                0 -> MALE
                1 -> FEMALE
                else -> throw IllegalArgumentException("Unknown gender value: $value")
            }
        }

        fun GenderDto.toInt(): Int {
            return when (this) {
                MALE -> 0
                FEMALE -> 1
            }
        }
    }
}
