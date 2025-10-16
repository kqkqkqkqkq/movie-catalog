package k.movie_catalog.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import k.movie_catalog.api.routes.AuthApi
import k.movie_catalog.api.routes.FavouritesApi
import k.movie_catalog.api.routes.MovieApi
import k.movie_catalog.api.routes.ReviewApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

class RetrofitClient(
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient,
    private val json: Json,
) {
    private val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(jsonConverterFactory)
        .build()

    val authApi: AuthApi by lazy {
        retrofit.create<AuthApi>()
    }

    val favouritesApi: FavouritesApi by lazy {
        retrofit.create<FavouritesApi>()
    }

    val movieApi: MovieApi by lazy {
        retrofit.create<MovieApi>()
    }

    val reviewApi: ReviewApi by lazy {
        retrofit.create<ReviewApi>()
    }
}