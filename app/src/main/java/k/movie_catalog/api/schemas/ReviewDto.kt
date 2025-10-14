package k.movie_catalog.api.schemas

import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

@Serializable
data class ReviewDto(
    val id: UUID,
    val rating: Int,
    val reviewText: String?,
    val isAnonymous: Boolean,
    val createDateTime: Date,
    val author: UserShortDto,
)
