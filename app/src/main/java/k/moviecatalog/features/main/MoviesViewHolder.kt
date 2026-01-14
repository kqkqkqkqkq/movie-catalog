package k.moviecatalog.features.main

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import k.moviecatalog.R
import k.moviecatalog.constants.UiConstants
import k.moviecatalog.databinding.ItemMovieBinding
import k.moviecatalog.repositories.models.MovieElement
import k.moviecatalog.repositories.models.ReviewShort

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

        binding.title.text = movie.name ?: "*****"
        binding.rating.text = ratingValue.toString()
        binding.rating.chipBackgroundColor = getColorByRating(
            binding.root.context, ratingValue
        )
        binding.genres.text = movie.genres.joinToString { it.name ?: "" }
        binding.year.text = movie.year.toString()
        binding.country.text = movie.country.toString()
        moviePoster.load(movie.poster) {
            crossfade(true)
            placeholder(R.drawable.icon_movie_catalog)
            error(R.drawable.icon_movie_catalog)
        }
    }

    private fun calculateRating(reviews: List<ReviewShort>): Double {
        if (reviews.isEmpty()) return 0.0

        val sum = reviews.sumOf { it.rating.toDouble() }
        val average = sum / reviews.size
        return "%.1f".format(average).toDouble()
    }

    private fun getColorByRating(context: Context, rating: Double): ColorStateList {
        val colorRes = when {
            rating >= UiConstants.HIGH_RATING -> R.color.ratingHigh
            rating > UiConstants.MEDIUM_RATING -> R.color.ratingMedium
            rating > UiConstants.LOW_RATING -> R.color.ratingLow
            else -> R.color.ratingUnknown
        }
        return ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    }
}