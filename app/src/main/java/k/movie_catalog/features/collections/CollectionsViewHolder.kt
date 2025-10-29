package k.movie_catalog.features.collections

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import k.movie_catalog.databinding.ItemCollectionBinding
import k.movie_catalog.repositories.models.Collection

class CollectionsViewHolder(
    view: View,
    private val onCollectionClick: (Collection) -> Unit,
): RecyclerView.ViewHolder(view) {
    private val binding = ItemCollectionBinding.bind(view)

    fun bind(collection: Collection) = with(binding) {
        binding.itemCollectionsCard.setOnClickListener {
            onCollectionClick(collection)
        }
        binding.title.setText(collection.title)
//        binding.icon.setImageIcon(collection)
        // TODO("add icon")
    }
}