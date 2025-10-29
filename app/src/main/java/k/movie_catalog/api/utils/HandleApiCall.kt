package k.movie_catalog.api.utils

import retrofit2.Response

suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>) = runCatching {
    val response = apiCall()
    if (response.isSuccessful) {
        val body = response.body()
        body ?: throw Exception("Response body is null")
    } else {
        throw Exception("HTTP ${response.code()}: ${response.message()}")
    }
}