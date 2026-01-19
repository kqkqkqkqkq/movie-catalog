package k.moviecatalog.utils.mapper.genre

import k.moviecatalog.api.schemas.GenreDto
import k.moviecatalog.repositories.models.Genre

fun Genre.toGenreDto() = GenreDto(
    id = id,
    name = name,
)

fun GenreDto.toGenre() = Genre(
    id = id,
    name = name,
)