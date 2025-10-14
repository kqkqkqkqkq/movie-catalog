package k.movie_catalog.api.schemas

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ReviewShortDto(
    val id: UUID,
    val rating: Int,
)
