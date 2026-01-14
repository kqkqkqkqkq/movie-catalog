package k.moviecatalog.features.collections.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import k.moviecatalog.repositories.models.Collection

sealed class CollectionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(collection: Collection)
}