package k.movie_catalog.features.main

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import coil3.request.error
import k.movie_catalog.R
import k.movie_catalog.databinding.ItemMovieBinding
import k.movie_catalog.repositories.models.MovieElement

class MoviesViewHolder(
    view: View,
    private val onMovieClick: (MovieElement) -> Unit,
) : RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)

    fun bind(movie: MovieElement) = with(binding) {
        binding.itemMovie.setOnClickListener {
            onMovieClick(movie)
        }
        val rating = if (movie.reviews.isNotEmpty()) {
            movie.reviews.sumOf { it.rating } / movie.reviews.size
        } else {
            0
        }
        val ratingColor = when {
            rating >= 7 -> R.color.ratingHigh
            rating > 4 && rating < 7 -> R.color.ratingMedium
            else -> R.color.ratingLow
        }
        val colorState = ColorStateList.valueOf(
            ContextCompat.getColor(binding.root.context, ratingColor)
        )
        binding.title.text = movie.name ?: "***"
        binding.rating.text = rating.toString()
        binding.rating.chipBackgroundColor = colorState
        binding.genres.text = movie.genres.joinToString { it.name ?: "" }
        binding.year.text = movie.year.toString()
        binding.country.text = movie.country.toString()
        moviePoster.load(movie.poster) {
            crossfade(true)
            placeholder(R.drawable.icon_movie_catalog)
            error(R.drawable.icon_movie_catalog)
        }
    }
}