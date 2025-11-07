package k.movie_catalog.features.collections.icons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import k.movie_catalog.R

class IconAdapter(
    private val onIconClick: (CollectionIcon) -> Unit,
) : ListAdapter<CollectionIcon, IconViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_icon, parent, false)
        return IconViewHolder(view, onIconClick)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<CollectionIcon>() {
            override fun areItemsTheSame(oldItem: CollectionIcon, newItem: CollectionIcon) =
                oldItem.icon == newItem.icon

            override fun areContentsTheSame(oldItem: CollectionIcon, newItem: CollectionIcon) =
                oldItem == newItem
        }
    }
}