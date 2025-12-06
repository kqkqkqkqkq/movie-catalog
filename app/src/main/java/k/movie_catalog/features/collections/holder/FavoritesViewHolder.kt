package k.movie_catalog.features.collections.holder

import android.view.View
import k.movie_catalog.R
import k.movie_catalog.databinding.ItemCollectionBinding
import k.movie_catalog.repositories.models.Collection

class FavoritesViewHolder(
    view: View,
    private val onFavoritesClick: (Collection) -> Unit,
) : CollectionsViewHolder(view) {
    private val binding = ItemCollectionBinding.bind(view)

    override fun bind(collection: Collection) = with(binding) {
        binding.itemCollectionsCard.setOnClickListener {
            onFavoritesClick(collection)
        }
        binding.title.text = collection.title
        binding.icon.setImageResource(collection.icon ?: R.drawable.icon_heart_filled)
    }
}