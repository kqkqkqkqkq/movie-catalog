package k.moviecatalog.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import k.moviecatalog.R
import k.moviecatalog.repositories.models.MovieElement

class MoviesAdapter(
    private val onMovieClick: (MovieElement) -> Unit,
) : ListAdapter<MovieElement, MoviesViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view, onMovieClick)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
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