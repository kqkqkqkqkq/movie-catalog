package k.moviecatalog.api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModifyDto(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean,
)