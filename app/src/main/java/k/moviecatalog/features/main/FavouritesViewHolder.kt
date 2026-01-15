package k.moviecatalog.features.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import k.moviecatalog.databinding.ItemFavouritesBinding
import k.moviecatalog.repositories.models.MovieElement

class FavouritesViewHolder(
    view: View,
    private val onFavouriteClick: (MovieElement) -> Unit,
    private val onDeleteClick: (MovieElement) -> Unit,
) : RecyclerView.ViewHolder(view) {

    private val binding = ItemFavouritesBinding.bind(view)

    fun bind(movie: MovieElement) = with(binding) {
        favouritePoster.setOnClickListener {
            onFavouriteClick(movie)
        }
        favouritePoster.load(movie.poster) {
            // TODO("fix: application crash when call placeholder or crossfade or error")
//            crossfade(true)
//            placeholder(R.drawable.icon_movie_catalog)
//            error(R.drawable.icon_movie_catalog)
        }
        removeButton.setOnClickListener {
            onDeleteClick(movie)
        }
    }
}