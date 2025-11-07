package k.movie_catalog.api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class MoviesPagedListDto(
    val movies: List<MovieElementDto>,
    val pageInfo: PageInfoDto,
)
