package k.movie_catalog.api.schemas

import k.movie_catalog.utils.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class GenreDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String?,
)
