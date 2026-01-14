package k.moviecatalog.features.collections.details

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import k.moviecatalog.R
import k.moviecatalog.databinding.ItemCollectionMovieBinding
import k.moviecatalog.repositories.models.CollectionMovie

class DetailsCollectionViewHolder(
    view: View,
    private val onCollectionMovieClick: (CollectionMovie) -> Unit,
) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCollectionMovieBinding.bind(view)

    fun bind(collectionMovie: CollectionMovie) {
        binding.root.setOnClickListener {
            onCollectionMovieClick(collectionMovie)
        }
        binding.title.text = collectionMovie.title
        binding.description.text = collectionMovie.description
        binding.collectionMoviePoster.load(collectionMovie.posterUrl) {
            crossfade(true)
            placeholder(R.drawable.icon_movie_catalog)
            error(R.drawable.icon_movie_catalog)
        }
    }
}