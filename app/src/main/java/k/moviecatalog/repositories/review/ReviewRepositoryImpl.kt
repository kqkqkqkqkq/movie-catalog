package k.moviecatalog.repositories.review

import k.moviecatalog.api.routes.ReviewApi
import k.moviecatalog.api.utils.handleApiCall
import k.moviecatalog.repositories.models.ReviewModify
import k.moviecatalog.utils.mapper.review.toReviewModifyDto
import java.util.UUID

class ReviewRepositoryImpl(
    private val reviewApi: ReviewApi,
) : ReviewRepository {
    override suspend fun addReview(movieId: UUID, review: ReviewModify) = handleApiCall {
        reviewApi.addReview(movieId, review.toReviewModifyDto())
    }

    override suspend fun updateReview(movieId: UUID, reviewId: UUID, review: ReviewModify) = handleApiCall {
        reviewApi.updateReview(movieId, reviewId, review.toReviewModifyDto())
    }

    override suspend fun deleteReview(movieId: UUID, reviewId: UUID) = handleApiCall {
        reviewApi.deleteReview(movieId, reviewId)
    }
}