package k.movie_catalog.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import k.movie_catalog.api.api.AuthApi
import k.movie_catalog.api.api.FavouritesApi
import k.movie_catalog.api.api.MovieApi
import k.movie_catalog.api.api.ReviewApi
import k.movie_catalog.api.routes.Routes
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }
    val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Routes.BASE_URL + Routes.API_VERSION)
        .client(okHttpClient)
        .addConverterFactory(jsonConverterFactory)
        .build()

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val favouritesApi: FavouritesApi by lazy {
        retrofit.create(FavouritesApi::class.java)
    }

    val movieApi: MovieApi by lazy {
        retrofit.create(MovieApi::class.java)
    }

    val reviewApi: ReviewApi by lazy {
        retrofit.create(ReviewApi::class.java)
    }
}