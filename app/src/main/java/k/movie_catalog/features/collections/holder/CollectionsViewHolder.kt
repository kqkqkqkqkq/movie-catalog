package k.movie_catalog.features.collections.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import k.movie_catalog.repositories.models.Collection

sealed class CollectionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(collection: Collection)
}