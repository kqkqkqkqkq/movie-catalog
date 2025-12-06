package k.movie_catalog.api.schemas

import k.movie_catalog.api.serializer.GenderDtoSerializer
import kotlinx.serialization.Serializable

@Serializable(with = GenderDtoSerializer::class)
enum class GenderDto {
    MALE,
    FEMALE,
}
