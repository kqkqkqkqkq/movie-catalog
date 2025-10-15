package k.movie_catalog.api.routes

object Routes {
    const val BASE_URL = "https://react-midterm.kreosoft.space/"
    const val API_VERSION = "api/"

    private const val AUTH_ROUTE = "account/"
    private const val FAVOURITE_ROUTE = "favourites/"
    private const val MOVIES_ROUTE = "movies/"
    private const val MOVIE_ROUTE = "movie/"
    private const val REVIEW_ROUTE = "review/"

    const val REGISTER = "${AUTH_ROUTE}register/"
    const val LOGIN = "${AUTH_ROUTE}login/"
    const val LOGOUT = "${AUTH_ROUTE}logout/"
    const val PROFILE = "${AUTH_ROUTE}profile/"
    const val UPDATE_PROFILE = "${AUTH_ROUTE}profile/"

    const val FAVOURITES = FAVOURITE_ROUTE
    const val ADD_FAVOURITE = "$FAVOURITE_ROUTE/{id}/add"
    const val DELETE_FAVOURITE = "$FAVOURITE_ROUTE/{id}/delete"

    const val MOVIES = "$MOVIES_ROUTE/{page}"
    const val MOVIE_DETAILS = "$MOVIES_ROUTE/details/{id}"

    const val ADD_REVIEW = "$MOVIE_ROUTE/{movieId}$REVIEW_ROUTE/add"
    const val UPDATE_REVIEW = "$MOVIE_ROUTE/{movieId}$REVIEW_ROUTE/{id}/edit"
    const val DELETE_REVIEW = "$MOVIE_ROUTE/{movieId}$REVIEW_ROUTE/{id}/delete"
}