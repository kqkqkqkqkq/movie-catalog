package k.movie_catalog.api.schemas

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class GenreDto(
    val id: UUID,
    val name: String?,
)
