package k.moviecatalog.repositories.models

data class MoviesPagedList(
    val movies: List<MovieElement>,
    val pageInfo: PageInfo,
)
