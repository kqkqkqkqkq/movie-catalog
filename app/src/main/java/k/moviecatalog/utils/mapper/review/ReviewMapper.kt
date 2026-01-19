package k.moviecatalog.utils.mapper.review

import k.moviecatalog.api.schemas.ReviewDto
import k.moviecatalog.api.schemas.ReviewModifyDto
import k.moviecatalog.api.schemas.ReviewShortDto
import k.moviecatalog.repositories.models.Review
import k.moviecatalog.repositories.models.ReviewModify
import k.moviecatalog.repositories.models.ReviewShort
import k.moviecatalog.utils.mapper.user.toUserShort
import k.moviecatalog.utils.mapper.user.toUserShortDto

fun Review.toReviewShort() = ReviewShort(
    id = id,
    rating = rating,
)

fun Review.toReviewDto() = ReviewDto(
    id = id,
    rating = rating,
    reviewText = reviewText,
    isAnonymous = isAnonymous,
    createDateTime = createDateTime,
    author = author?.toUserShortDto()
)

fun ReviewShort.toReviewShortDto() = ReviewShortDto(
    id = id,
    rating = rating,
)

fun ReviewDto.toReview() = Review(
    id = id,
    rating = rating,
    reviewText = reviewText,
    isAnonymous = isAnonymous,
    createDateTime = createDateTime,
    author = author?.toUserShort(),
)

fun ReviewModify.toReviewModifyDto() = ReviewModifyDto(
    reviewText = reviewText,
    rating = rating,
    isAnonymous = isAnonymous,
)

fun ReviewModifyDto.toReviewModify() = ReviewModify(
    reviewText = reviewText,
    rating = rating,
    isAnonymous = isAnonymous,
)

fun Review.toReviewModify() = ReviewModify(
    reviewText = reviewText.orEmpty(),
    rating = rating,
    isAnonymous = isAnonymous,
)
