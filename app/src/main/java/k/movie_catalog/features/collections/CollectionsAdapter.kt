package k.movie_catalog.features.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import k.movie_catalog.R
import k.movie_catalog.repositories.models.Collection

class CollectionsAdapter(
    private val onCollectionClick: (Collection) -> Unit,
) : ListAdapter<Collection, CollectionsViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection, parent, false)
        return CollectionsViewHolder(view, onCollectionClick)
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Collection>() {
            override fun areItemsTheSame(oldItem: Collection, newItem: Collection) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Collection, newItem: Collection) =
                oldItem == newItem

        }
    }
}