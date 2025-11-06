package k.movie_catalog.features.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import k.movie_catalog.R
import k.movie_catalog.databinding.ItemFavouritesBinding
import k.movie_catalog.repositories.models.MovieElement

class FavouritesViewHolder(
    view: View,
    private val onFavouriteClick: (MovieElement) -> Unit,
) : RecyclerView.ViewHolder(view) {

    private val binding = ItemFavouritesBinding.bind(view)

    fun bind(movie: MovieElement) = with(binding) {
        favouritePoster.setOnClickListener {
            onFavouriteClick(movie)
        }
        favouritePoster.load(movie.poster) {
            crossfade(true)
            placeholder(R.drawable.icon_movie_catalog)
            error(R.drawable.icon_movie_catalog)
        }
    }
}