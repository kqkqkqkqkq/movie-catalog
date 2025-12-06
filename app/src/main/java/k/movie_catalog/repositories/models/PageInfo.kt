package k.movie_catalog.repositories.models

data class PageInfo(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int,
)
