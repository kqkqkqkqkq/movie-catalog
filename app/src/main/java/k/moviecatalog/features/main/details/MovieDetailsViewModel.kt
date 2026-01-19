package k.moviecatalog.features.main.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.moviecatalog.App
import k.moviecatalog.common.dispatcher.DispatcherProvider
import k.moviecatalog.common.logger.movieCatalogLogger
import k.moviecatalog.repositories.auth.AuthRepository
import k.moviecatalog.repositories.collections.CollectionsRepository
import k.moviecatalog.repositories.favourites.FavouritesRepository
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.Review
import k.moviecatalog.repositories.movie.MoviesRepository
import k.moviecatalog.repositories.review.ReviewRepository
import k.moviecatalog.utils.mapper.movie.toCollectionMovie
import k.moviecatalog.utils.mapper.movie.toMovieElement
import k.moviecatalog.utils.mapper.review.toReviewModify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Если бы здесь было 8 зависимостей (constructorThreshold: 8),
 * то пришлось бы вынести их в отдельный интерфейс.
 * Например:
 * interface MovieDetailsViewModelDependencies
 */
class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository = App.instance.moviesRepository,
    private val favouritesRepository: FavouritesRepository = App.instance.favouritesRepository,
    private val collectionsRepository: CollectionsRepository = App.instance.collectionsRepository,
    private val reviewRepository: ReviewRepository = App.instance.reviewRepository,
    private val authRepository: AuthRepository = App.instance.authRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
) : ViewModel() {

    private val _movieDetailState = MutableStateFlow(MovieDetailsUiState())
    val movieDetailState = _movieDetailState.asStateFlow()

    init {
        getCurrentUserProfile()
        getAvailableCollections()
    }

    fun onFavourite() {
        val movie = _movieDetailState.value.movie?.toMovieElement() ?: return
        viewModelScope.launch(dispatcherProvider.io) {
            try {
                val isFavourite = _movieDetailState.value.inFavourites
                if (isFavourite) {
                    favouritesRepository.deleteFavourite(movie)
                } else {
                    favouritesRepository.addFavourite(movie)
                }
                _movieDetailState.update { it.copy(inFavourites = !isFavourite) }
            } catch (e: Exception) {
                _movieDetailState.update {
                    it.copy(error = e.message ?: "Favourite update failed")
                }
            }
        }
    }

    fun loadMovieDetails(id: UUID) {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(isLoading = true) }
            moviesRepository.getMovieDetails(id).onSuccess { movieDetails ->
                val currentReviews = movieDetails.reviews
                val currentUserProfile = _movieDetailState.value.currentUserProfile
                val reorderedReviews =
                    moveCurrentUserReviewTop(currentReviews, currentUserProfile?.id)
                _movieDetailState.update { it.copy(movie = movieDetails.copy(reviews = reorderedReviews)) }
                favouritesRepository.getFavourites().onSuccess { favourites ->
                    _movieDetailState.update { it.copy(inFavourites = id in favourites.map { fav -> fav.id }) }
                }.onFailure { e ->
                    _movieDetailState.update { it.copy(error = e.message) }
                }
            }.onFailure { e ->
                _movieDetailState.update { it.copy(error = e.message) }
            }
            _movieDetailState.update { it.copy(isLoading = false) }
        }
    }

    fun addToCollection(collection: Collection) {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(isLoading = true) }
            val movie = _movieDetailState.value.movie
            if (movie != null) {
                collectionsRepository.addMovieToCollection(collection, movie.toCollectionMovie())
            } else {
                _movieDetailState.update { it.copy(error = "Movie is null") }
            }
            _movieDetailState.update { it.copy(isLoading = false) }
        }
    }

    fun createReview(review: Review) {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(isLoading = true) }
            val movieId = requireNotNull(_movieDetailState.value.movie?.id)
            reviewRepository.addReview(movieId = movieId, review = review.toReviewModify())
                .onSuccess {
                    val currentMovie = _movieDetailState.value.movie
                    val currentReviews = requireNotNull(_movieDetailState.value.movie?.reviews)
                    _movieDetailState.update { it.copy(movie = currentMovie?.copy(reviews = listOf(review) + currentReviews)) }
                    movieCatalogLogger().d("[Create Review]", "Review created: $review")
                }.onFailure { e ->
                    _movieDetailState.update { it.copy(error = e.message) }
                    movieCatalogLogger().e("[Create Review]", e.message.toString())
                }
            _movieDetailState.update { it.copy(isLoading = false) }
        }
    }

    fun deleteReview(review: Review) {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(isLoading = true) }
            val movieId = requireNotNull(_movieDetailState.value.movie?.id)
            reviewRepository.deleteReview(movieId = movieId, reviewId = review.id).onSuccess {
                val currentMovie = _movieDetailState.value.movie
                val currentReviews = requireNotNull(_movieDetailState.value.movie?.reviews)
                _movieDetailState.update { it.copy(movie = currentMovie?.copy(reviews = currentReviews - review)) }
                movieCatalogLogger().d("[Delete Review]", "Review deleted")
            }.onFailure { e ->
                _movieDetailState.update { it.copy(error = e.message) }
                movieCatalogLogger().e("[Delete Review]", e.message.toString())
            }
            _movieDetailState.update { it.copy(isLoading = false) }
        }
    }

    fun updateReview(review: Review) {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(isLoading = true) }
            val movieId = requireNotNull(_movieDetailState.value.movie?.id)
            reviewRepository.updateReview(movieId = movieId, reviewId = review.id).onSuccess { r ->
                // TODO("update local list with new data")
                movieCatalogLogger().d("[Update Review]", r.toString())
            }.onFailure { e ->
                _movieDetailState.update { it.copy(error = e.message) }
                movieCatalogLogger().e("[Update Review]", e.message.toString())
            }
            _movieDetailState.update { it.copy(isLoading = false) }
        }
    }

    fun back() {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(error = null) }
        }
    }

    private fun getCurrentUserProfile() {
        viewModelScope.launch(dispatcherProvider.io) {
            authRepository.getProfile().onSuccess { profile ->
                _movieDetailState.update { it.copy(currentUserProfile = profile) }
            }.onFailure {
                _movieDetailState.update { it.copy(error = "It is impossible to get current user") }
            }
        }
    }

    private fun getAvailableCollections() {
        viewModelScope.launch(dispatcherProvider.io) {
            _movieDetailState.update { it.copy(isLoading = true) }
            collectionsRepository.getCollections().onSuccess { collections ->
                _movieDetailState.update { it.copy(availableCollections = collections) }
            }.onFailure {
                _movieDetailState.update { it.copy(error = "Cannot pull available collections") }
            }
            _movieDetailState.update { it.copy(isLoading = false) }
        }
    }

    private fun moveCurrentUserReviewTop(reviews: List<Review>, currentUserId: UUID?): List<Review> {
        if (currentUserId == null) return reviews
        val (mine, others) = reviews.partition { it.author?.userId == currentUserId }
        val sortedMine = mine.sortedByDescending { it.createDateTime }
        return sortedMine + others
    }
}