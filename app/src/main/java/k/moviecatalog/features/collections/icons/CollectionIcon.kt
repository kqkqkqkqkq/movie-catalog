package k.moviecatalog.features.collections.icons

import k.moviecatalog.R

data class CollectionIcon(
    val icon: Int,
) {
    companion object {
        private val resources = listOf(
            R.drawable.icon_heart,
            R.drawable.icon_star,
            R.drawable.icon_clock,
            R.drawable.icon_book,
            R.drawable.icon_moon,
        )

        val icons = List(50) { index ->
            CollectionIcon(resources[index % resources.size])
        }
    }
}
