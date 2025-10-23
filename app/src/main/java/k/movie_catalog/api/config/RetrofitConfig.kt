package k.movie_catalog.api.config

import k.movie_catalog.constants.RetrofitConstants
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

import java.util.concurrent.TimeUnit

class RetrofitConfig {
    fun buildOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(RetrofitConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(RetrofitConstants.READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(RetrofitConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
        .build()

    fun buildJson() = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        explicitNulls = false
        coerceInputValues = true
    }
}