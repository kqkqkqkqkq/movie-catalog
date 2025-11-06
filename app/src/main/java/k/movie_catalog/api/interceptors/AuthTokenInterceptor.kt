package k.movie_catalog.api.interceptors

import k.movie_catalog.repositories.token.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(
    private val tokenRepository: TokenRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val shouldAddToken = originalRequest.header("No-Auth") == null
        if (!shouldAddToken) {
            val requestWithoutHeader = originalRequest.newBuilder()
                .removeHeader("No-Auth")
                .build()
            return chain.proceed(requestWithoutHeader)
        }

        val token = runBlocking {
            tokenRepository.getToken()
        }
        if (token != null) {
            val requestWithToken = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            return chain.proceed(requestWithToken)
        }

        return chain.proceed(originalRequest)
    }
}