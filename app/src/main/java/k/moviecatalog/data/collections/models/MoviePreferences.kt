package k.moviecatalog.data.collections.models

import k.moviecatalog.api.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MoviePreferences(
    val title: String? = null,
    val description: String? = null,
    val posterUrl: String? = null,
    @Serializable(with = UUIDSerializer::class)
    val movieId: UUID? = null,
)
