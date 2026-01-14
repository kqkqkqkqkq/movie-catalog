package k.moviecatalog.api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class MoviesListDto(
    val movies: List<MovieElementDto>,
)
