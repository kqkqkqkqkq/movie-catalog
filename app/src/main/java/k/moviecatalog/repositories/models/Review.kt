package k.moviecatalog.repositories.models

import java.time.LocalDateTime
import java.util.UUID

data class Review(
    val id: UUID,
    val rating: Int,
    val reviewText: String?,
    val isAnonymous: Boolean,
    val createDateTime: LocalDateTime,
    val author: UserShort,
)
