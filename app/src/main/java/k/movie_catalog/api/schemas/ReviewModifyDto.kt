package k.movie_catalog.api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModifyDto(
    val reviewText: String,
    val rating: Int, // TODO("validate between 0 and 10")
    val isAnonymous: Boolean,
)