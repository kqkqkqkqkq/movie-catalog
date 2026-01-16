package k.moviecatalog.features.main

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import k.moviecatalog.R
import k.moviecatalog.databinding.ItemMovieBinding
import k.moviecatalog.repositories.models.MovieElement
import k.moviecatalog.repositories.models.ReviewShort
import k.moviecatalog.utils.ui.ColorConverter
import kotlin.math.round

class MoviesViewHolder(
    view: View,
    private val onMovieClick: (MovieElement) -> Unit,
) : RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)

    fun bind(movie: MovieElement) = with(binding) {
        binding.itemMovie.setOnClickListener {
            onMovieClick(movie)
        }

        val ratingValue = calculateRating(movie.reviews)
        val ratingColor = ColorConverter.calculateColor(
            rating = ratingValue,
            lowColor = ContextCompat.getColor(binding.root.context, R.color.ratingLow),
            midColor = ContextCompat.getColor(binding.root.context, R.color.ratingMedium),
            highColor = ContextCompat.getColor(binding.root.context, R.color.ratingHigh),
        )

        binding.title.text = movie.name.orEmpty()
        binding.rating.text = ratingValue.toString()
        binding.rating.chipBackgroundColor = ColorStateList.valueOf(ratingColor)
        binding.genres.text = movie.genres.joinToString { it.name.orEmpty() }
        binding.year.text = movie.year.toString()
        binding.country.text = movie.country.toString()
        moviePoster.load(movie.poster) {
            // TODO("fix: application crash when call placeholder or crossfade or error")
//            crossfade(true)
//            placeholder(R.drawable.icon_movie_catalog)
//            error(R.drawable.icon_movie_catalog)
        }
    }

    private fun calculateRating(reviews: List<ReviewShort>, format: Int = 10): Double {
        if (reviews.isEmpty()) return 0.0
        val average = reviews.sumOf { it.rating.toDouble() } / reviews.size
        return round(average * format) / format
    }
}