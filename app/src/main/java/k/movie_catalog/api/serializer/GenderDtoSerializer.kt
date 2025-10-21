package k.movie_catalog.api.serializer

import k.movie_catalog.api.schemas.GenderDto
import k.movie_catalog.api.schemas.GenderDto.Companion.toInt
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object GenderDtoSerializer : KSerializer<GenderDto?> {
    override val descriptor = PrimitiveSerialDescriptor("GenderDto", PrimitiveKind.INT)

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: GenderDto?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            encoder.encodeInt(value.toInt())
        }
    }

    override fun deserialize(decoder: Decoder): GenderDto? {
        return try {
            val intValue = decoder.decodeInt()
            GenderDto.fromInt(intValue)
        } catch (e: Exception) {
            null
        }
    }
}