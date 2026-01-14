package k.moviecatalog.api.schemas

import k.moviecatalog.api.serializer.GenderDtoSerializer
import kotlinx.serialization.Serializable

@Serializable(with = GenderDtoSerializer::class)
enum class GenderDto {
    MALE,
    FEMALE,
}
