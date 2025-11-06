package k.movie_catalog.api.interceptors

import k.movie_catalog.repositories.token.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class UnauthorizedInterceptor(
    private val tokenRepository: TokenRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == 401) {
            runBlocking {
                tokenRepository.clearToken()
            }
        }
        return response
    }
}