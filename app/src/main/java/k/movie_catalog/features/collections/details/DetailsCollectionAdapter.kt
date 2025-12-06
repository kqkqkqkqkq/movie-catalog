package k.movie_catalog.features.collections.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import k.movie_catalog.R
import k.movie_catalog.repositories.models.CollectionMovie

class DetailsCollectionAdapter(
    private val onCollectionMovieClick: (CollectionMovie) -> Unit,
) : ListAdapter<CollectionMovie, DetailsCollectionViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsCollectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection_movie, parent, false)
        return DetailsCollectionViewHolder(view, onCollectionMovieClick)
    }

    override fun onBindViewHolder(holder: DetailsCollectionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<CollectionMovie>() {
            override fun areItemsTheSame(oldItem: CollectionMovie, newItem: CollectionMovie) =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: CollectionMovie, newItem: CollectionMovie) =
                oldItem == newItem
        }
    }
}