package k.moviecatalog.repositories.models

import java.util.UUID

data class ReviewShort(
    val id: UUID,
    val rating: Int,
)