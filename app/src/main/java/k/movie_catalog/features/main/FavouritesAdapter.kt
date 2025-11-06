package k.movie_catalog.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import k.movie_catalog.R
import k.movie_catalog.repositories.models.MovieElement

class FavouritesAdapter(
    private val onFavouriteClick: (MovieElement) -> Unit,
) : ListAdapter<MovieElement, FavouritesViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return FavouritesViewHolder(view, onFavouriteClick)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<MovieElement>() {
            override fun areItemsTheSame(oldItem: MovieElement, newItem: MovieElement) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieElement, newItem: MovieElement) =
                oldItem == newItem

        }
    }
}