package k.moviecatalog.features.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import k.moviecatalog.R
import k.moviecatalog.features.collections.holder.CollectionViewHolder
import k.moviecatalog.features.collections.holder.CollectionsViewHolder
import k.moviecatalog.features.collections.holder.FavoritesViewHolder
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.CollectionMovie

class CollectionsAdapter(
    private val favouriteMovies: List<CollectionMovie>,
    private val onCollectionClick: (Collection) -> Unit,
    private val onFavouriteClick: (Collection) -> Unit,
) : ListAdapter<Collection, CollectionsViewHolder>(DIFF) {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_FAVORITES else VIEW_TYPE_COLLECTION
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        return when (viewType) {
            VIEW_TYPE_FAVORITES -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_collection, parent, false)
                FavoritesViewHolder(view, onFavouriteClick)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_collection, parent, false)
                CollectionViewHolder(view, onCollectionClick)
            }
        }
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        when (holder) {
            is FavoritesViewHolder -> {
                val favoritesCollection = Collection(
                    title = "Favourites",
                    icon = R.drawable.icon_heart_filled,
                    movies = favouriteMovies,
                    isFavourite = true,
                )
                holder.bind(favoritesCollection)
            }

            is CollectionViewHolder -> {
                holder.bind(getItem(position - 1))
            }
        }
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Collection>() {
            override fun areItemsTheSame(oldItem: Collection, newItem: Collection) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Collection, newItem: Collection) =
                oldItem == newItem
        }

        const val VIEW_TYPE_FAVORITES = 0
        const val VIEW_TYPE_COLLECTION = 1
    }
}