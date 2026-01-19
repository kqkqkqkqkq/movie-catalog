package k.moviecatalog.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import k.moviecatalog.api.routes.AuthApi
import k.moviecatalog.api.routes.FavouritesApi
import k.moviecatalog.api.routes.MovieApi
import k.moviecatalog.api.routes.ReviewApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

class RetrofitClient(
    json: Json,
    okHttpClient: OkHttpClient,
    private val baseUrl: String,
) {
    private val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(jsonConverterFactory)
        .build()

    val authApi by lazy { retrofit.create<AuthApi>() }

    val favouritesApi by lazy { retrofit.create<FavouritesApi>() }

    val movieApi by lazy { retrofit.create<MovieApi>() }

    val reviewApi by lazy { retrofit.create<ReviewApi>() }
}