package k.movie_catalog

sealed interface ScreenState {
    data object Initial : ScreenState
    data object Loading : ScreenState
    data class Error(val message: String) : ScreenState
    data class Content<T>(val content: T) : ScreenState
}