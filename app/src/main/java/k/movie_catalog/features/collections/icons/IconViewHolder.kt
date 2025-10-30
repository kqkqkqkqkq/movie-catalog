package k.movie_catalog.features.collections.icons

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import k.movie_catalog.databinding.ItemIconBinding

class IconViewHolder(
    view: View,
    private val onIconClick: (CollectionIcon) -> Unit,
) : RecyclerView.ViewHolder(view) {
    private val binding = ItemIconBinding.bind(view)

    fun bind(icon: CollectionIcon) = with(binding) {
        binding.iconContainer.setOnClickListener {
            onIconClick(icon)
        }
        binding.icon.setImageResource(icon.icon)
    }
}