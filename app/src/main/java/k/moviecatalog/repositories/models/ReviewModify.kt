package k.moviecatalog.repositories.models

data class ReviewModify(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean,
)
