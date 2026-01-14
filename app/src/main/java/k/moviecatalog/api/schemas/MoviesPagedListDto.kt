package k.moviecatalog.api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class MoviesPagedListDto(
    val movies: List<MovieElementDto>,
    val pageInfo: PageInfoDto,
)
