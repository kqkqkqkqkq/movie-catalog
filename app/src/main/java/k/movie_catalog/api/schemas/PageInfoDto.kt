package k.movie_catalog.api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class PageInfoDto(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int,
)
