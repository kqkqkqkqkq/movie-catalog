package k.moviecatalog.features.collections.holder

import android.view.View
import k.moviecatalog.R
import k.moviecatalog.databinding.ItemCollectionBinding
import k.moviecatalog.repositories.models.Collection

class CollectionViewHolder(
    view: View,
    private val onCollectionClick: (Collection) -> Unit,
) : CollectionsViewHolder(view) {
    private val binding = ItemCollectionBinding.bind(view)

    override fun bind(collection: Collection) = with(binding) {
        binding.itemCollectionsCard.setOnClickListener {
            onCollectionClick(collection)
        }
        binding.title.text = collection.title
        binding.icon.setImageResource(collection.icon ?: R.drawable.icon_heart)
    }
}