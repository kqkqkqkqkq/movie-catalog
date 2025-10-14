package k.movie_catalog.api.routes

object Routes {
    private const val API_VERSION = "/api"
    private const val BASE_URL = "https://react-midterm.kreosoft.space"

    private const val AUTH_ROUTE = "/account"
    private const val FAVOURITE_ROUTE = "/favourites"
    private const val MOVIES_ROUTE = "/movies"
    private const val MOVIE_ROUTE = "/movie"
    private const val REVIEW_ROUTE = "/review"

    const val REGISTER = "$BASE_URL$API_VERSION$AUTH_ROUTE/register"
    const val LOGIN = "$BASE_URL$API_VERSION$AUTH_ROUTE/login"
    const val LOGOUT = "$BASE_URL$API_VERSION$AUTH_ROUTE/logout"
    const val PROFILE = "$BASE_URL$API_VERSION$AUTH_ROUTE/profile"
    const val UPDATE_PROFILE = "$BASE_URL$API_VERSION$AUTH_ROUTE/profile"

    const val FAVOURITES = "$BASE_URL$API_VERSION$FAVOURITE_ROUTE"
    const val ADD_FAVOURITE = "$BASE_URL$API_VERSION$FAVOURITE_ROUTE/{id}/add"
    const val DELETE_FAVOURITE = "$BASE_URL$API_VERSION$FAVOURITE_ROUTE/{id}/delete"

    const val MOVIES = "$BASE_URL$API_VERSION$MOVIES_ROUTE/{page}"
    const val MOVIE_DETAILS = "$BASE_URL$API_VERSION$MOVIES_ROUTE/details/{id}"

    const val ADD_REVIEW = "$BASE_URL$API_VERSION$MOVIE_ROUTE/{movieId}$REVIEW_ROUTE/add"
    const val UPDATE_REVIEW = "$BASE_URL$API_VERSION$MOVIE_ROUTE/{movieId}$REVIEW_ROUTE/{id}/edit"
    const val DELETE_REVIEW = "$BASE_URL$API_VERSION$MOVIE_ROUTE/{movieId}$REVIEW_ROUTE/{id}/delete"
}