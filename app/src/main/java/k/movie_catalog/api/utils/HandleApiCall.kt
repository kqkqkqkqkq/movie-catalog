package k.movie_catalog.api.utils

import retrofit2.Response

suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>) = try {
    val response = apiCall()
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            Result.success(body)
        } else {
            Result.failure(Exception("Response body is null"))
        }
    } else {
        Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
    }
} catch (e: Exception) {
    Result.failure(e)
}
