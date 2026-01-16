package k.moviecatalog.utils.mapper.review

import k.moviecatalog.api.schemas.ReviewDto
import k.moviecatalog.api.schemas.ReviewShortDto
import k.moviecatalog.repositories.models.Review
import k.moviecatalog.repositories.models.ReviewShort
import k.moviecatalog.utils.mapper.user.toUserShort
import k.moviecatalog.utils.mapper.user.toUserShortDto

fun Review.toReviewShort() = ReviewShort(
    id = this.id,
    rating = this.rating,
)

fun Review.toReviewDto() = ReviewDto(
    id = this.id,
    rating = this.rating,
    reviewText = this.reviewText,
    isAnonymous = this.isAnonymous,
    createDateTime = this.createDateTime,
    author = this.author?.toUserShortDto()
)

fun ReviewShort.toReviewShortDto() = ReviewShortDto(
    id = this.id,
    rating = this.rating,
)

fun ReviewDto.toReview() = Review(
    id = this.id,
    rating = this.rating,
    reviewText = this.reviewText,
    isAnonymous = this.isAnonymous,
    createDateTime = this.createDateTime,
    author = this.author?.toUserShort(),
)