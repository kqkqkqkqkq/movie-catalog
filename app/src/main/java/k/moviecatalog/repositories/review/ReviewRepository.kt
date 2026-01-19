package k.moviecatalog.repositories.review

import k.moviecatalog.repositories.models.ReviewModify
import java.util.UUID

interface ReviewRepository {
    suspend fun addReview(movieId: UUID, review: ReviewModify): Result<Unit>
    suspend fun updateReview(movieId: UUID, reviewId: UUID): Result<ReviewModify>
    suspend fun deleteReview(movieId: UUID, reviewId: UUID): Result<Unit>
}