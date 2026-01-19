package k.moviecatalog.api.schemas

import k.moviecatalog.utils.serializer.GenderDtoSerializer
import kotlinx.serialization.Serializable

@Serializable(with = GenderDtoSerializer::class)
enum class GenderDto {
    MALE,
    FEMALE,
}
