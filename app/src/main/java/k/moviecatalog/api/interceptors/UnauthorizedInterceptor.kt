package k.moviecatalog.api.interceptors

import k.moviecatalog.repositories.token.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class UnauthorizedInterceptor(
    private val tokenRepository: TokenRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == NOT_AUTHORIZED_CODE) {
            runBlocking {
                tokenRepository.clearToken()
            }
        }
        return response
    }

    companion object {
        const val NOT_AUTHORIZED_CODE = 401
    }
}