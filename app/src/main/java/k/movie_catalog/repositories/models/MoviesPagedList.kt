package k.movie_catalog.repositories.models

data class MoviesPagedList(
    val movies: List<MovieElement>,
    val pageInfo: PageInfo,
)
