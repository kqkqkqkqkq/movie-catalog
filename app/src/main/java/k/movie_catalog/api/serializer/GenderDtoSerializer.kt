package k.movie_catalog.api.serializer

import k.movie_catalog.api.schemas.GenderDto
import k.movie_catalog.api.schemas.GenderDto.Companion.toInt
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object GenderDtoSerializer : KSerializer<GenderDto> {
    override val descriptor = PrimitiveSerialDescriptor("GenderDto", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: GenderDto) {
        encoder.encodeInt(value.toInt())
    }

    override fun deserialize(decoder: Decoder): GenderDto {
        val intValue = decoder.decodeInt()
        return GenderDto.Companion.fromInt(intValue)
    }
}