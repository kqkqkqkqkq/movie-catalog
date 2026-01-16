package k.moviecatalog.common.ui.swipe

import android.graphics.Canvas
import android.graphics.RectF
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.LinkedList
import kotlin.math.abs
import kotlin.math.max

/**
 * [Source](https://habr.com/ru/amp/publications/824348/)
 * [GitHub](https://github.com/Beladerm/SwipeHelper/blob/main/app/src/main/java/com/dch/swipehelper/SwipeHelper.kt)
 */

abstract class SwipeHelper(
    private val recyclerView: RecyclerView,
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.ACTION_STATE_IDLE,
    ItemTouchHelper.LEFT,
) {
    private var swipedPosition = -1
    private val buttonsBuffer: MutableMap<Int, List<UnderlayButton>> = mutableMapOf()
    private var swipeProgressMap = mutableMapOf<Int, Float>()
    private val recoverQueue = object : LinkedList<Int>() {
        override fun add(element: Int): Boolean {
            if (contains(element)) return false
            return super.add(element)
        }
    }

    private val touchListener = View.OnTouchListener { _, event ->
        if (swipedPosition < 0) return@OnTouchListener false
        buttonsBuffer[swipedPosition]?.forEach { it.handle(event) }
        recoverQueue.add(swipedPosition)
        swipedPosition = -1
        recoverSwipedItem()
        true
    }

    abstract fun instantiateButton(position: Int): List<UnderlayButton>

    init {
        recyclerView.setOnTouchListener(touchListener)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (swipedPosition != position) recoverQueue.add(swipedPosition)
        swipedPosition = position
        recoverSwipedItem()
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        val position = viewHolder.adapterPosition
        val progress = swipeProgressMap.getOrDefault(position, 0f)
        val swipeThreshold =
            if (progress > SWIPE_CONFIRM_PROGRESS_THRESHOLD) 1f else SWIPE_MIN_THRESHOLD
        return swipeThreshold
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return defaultValue
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val position = viewHolder.adapterPosition
        var maxDX = dX
        val itemView = viewHolder.itemView

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            swipeProgressMap[position] = abs(dX) / viewHolder.itemView.width.toFloat()
            if (dX < 0) {
                if (!buttonsBuffer.containsKey(position)) {
                    buttonsBuffer[position] = instantiateButton(position)
                }

                val buttons = buttonsBuffer[position] ?: return
                if (buttons.isEmpty()) return
                maxDX = max(-buttons.intrinsicWidth() * MAX_SWIPE_WIDTH_MULTIPLIER, dX)
                drawButtons(c, buttons, itemView, maxDX)
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, maxDX, dY, actionState, isCurrentlyActive)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        swipeProgressMap.remove(viewHolder.adapterPosition)
    }

    private fun recoverSwipedItem() {
        while (recoverQueue.isNotEmpty()) {
            val position = recoverQueue.poll() ?: return
            recyclerView.adapter?.notifyItemChanged(position)
        }
    }

    private fun drawButtons(
        canvas: Canvas,
        buttons: List<UnderlayButton>,
        itemView: View,
        dX: Float
    ) {
        var right = itemView.right
        buttons.forEach { button ->
            val width = button.intrinsicWidth / buttons.intrinsicWidth() * abs(dX)
            val left = right - width
            button.onDraw(
                canvas,
                RectF(left, itemView.top.toFloat(), right.toFloat(), itemView.bottom.toFloat())
            )
            right = left.toInt()
        }
    }

    companion object {
        const val MAX_SWIPE_WIDTH_MULTIPLIER = 1.5f
        const val SWIPE_CONFIRM_PROGRESS_THRESHOLD = 0.4f
        const val SWIPE_MIN_THRESHOLD = 0.05f
    }
}