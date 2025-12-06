package k.movie_catalog.repositories.models

data class ReviewModify(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean,
)
