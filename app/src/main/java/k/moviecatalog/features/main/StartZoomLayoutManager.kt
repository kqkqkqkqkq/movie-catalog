package k.moviecatalog.features.main

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StartZoomLayoutManager(
    context: Context,
    orientation: Int,
    reverseLayout: Boolean = false,
    private val shrinkAmount: Float = 0.17f,
) : LinearLayoutManager(context, orientation, reverseLayout) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        applyScaleToChildren()
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
        applyScaleToChildren()
        return scrolled
    }

    private fun applyScaleToChildren() {
        if (childCount == 0) return

        val startEdge = paddingLeft.toFloat()
        val endEdge = (width - paddingRight).toFloat()
        val s0 = 1f
        val s1 = 1f - shrinkAmount

        var chosenIndex = -1
        var minLeft = Float.MAX_VALUE
        for (i in 0 until childCount) {
            val child = getChildAt(i) ?: continue
            val left = getDecoratedLeft(child).toFloat()
            val right = getDecoratedRight(child).toFloat()
            if (left >= startEdge && right <= endEdge) {
                if (left < minLeft) {
                    minLeft = left
                    chosenIndex = i
                }
            }
        }

        for (i in 0 until childCount) {
            val child: View = getChildAt(i) ?: continue
            if (i == chosenIndex) {
                child.pivotX = 0f
                child.pivotY = child.height / 2f
                child.scaleX = s0
                child.scaleY = s0
            } else {
                child.scaleX = s1
                child.scaleY = s1
            }
        }
    }
}
