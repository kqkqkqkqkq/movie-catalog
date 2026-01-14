package k.moviecatalog.api.serializer

import k.moviecatalog.api.schemas.GenderDto
import k.moviecatalog.api.schemas.GenderDto.FEMALE
import k.moviecatalog.api.schemas.GenderDto.MALE
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
            val num = when (value) {
                MALE -> 0
                FEMALE -> 1
            }
            encoder.encodeInt(num)
        }
    }

    override fun deserialize(decoder: Decoder): GenderDto? {
        val intValue = decoder.decodeInt()
        println("DESERIALIZE: $intValue")
        return when (intValue) {
            0 -> MALE
            1 -> FEMALE
            else -> null
        }
    }
}