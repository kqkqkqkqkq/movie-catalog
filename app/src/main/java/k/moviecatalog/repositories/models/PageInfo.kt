package k.moviecatalog.repositories.models

data class PageInfo(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int,
)
