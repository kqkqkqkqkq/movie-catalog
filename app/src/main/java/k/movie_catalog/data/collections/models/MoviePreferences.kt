package k.movie_catalog.data.collections.models

import k.movie_catalog.api.serializer.UUIDSerializer
import k.movie_catalog.data.collections.CollectionsPreferencesSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MoviePreferences(
    val title: String? = null,
    val description: String? = null,
    @Serializable(with = UUIDSerializer::class)
    val movieId: UUID? = null,
)
