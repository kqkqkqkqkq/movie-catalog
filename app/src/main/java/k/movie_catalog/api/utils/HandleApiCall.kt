package k.movie_catalog.api.utils

import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> handleApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.success(apiCall())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}