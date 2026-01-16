package k.moviecatalog.common.ui.swipe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.view.MotionEvent
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import k.moviecatalog.R

/**
 * [Source](https://habr.com/ru/amp/publications/824348/)
 */
class UnderlayButton(
    textSize: Float = 12.0f,
    iconSize: Int = 24,
    private val context: Context,
    private val title: String,
    @ColorRes private val colorRes: Int,
    @DrawableRes private val iconRes: Int,
    private val contentColor: Int = ContextCompat.getColor(context, R.color.onErrorDark),
    private val clickListener: () -> Unit,
) {
    private var clickableRegion: RectF? = null
    private val textSizeInPixel = textSize * context.resources.displayMetrics.density
    private val iconSizeInPixel = iconSize * context.resources.displayMetrics.density
    private val horizontalPadding = 50.0f
    private val verticalPadding = horizontalPadding
    val intrinsicWidth: Float

    init {
        val paint = Paint()
        paint.textSize = textSizeInPixel
        paint.typeface = Typeface.DEFAULT_BOLD
        paint.textAlign = Paint.Align.LEFT
        val titleBounds = Rect()
        paint.getTextBounds(title, 0, title.length, titleBounds)
        intrinsicWidth = (titleBounds.width() + 2 * horizontalPadding)
    }

    fun onDraw(canvas: Canvas, rect: RectF) {
        val paint = Paint()
        paint.color = ContextCompat.getColor(context, colorRes)
        canvas.drawRect(rect, paint)

        val iconLeft = rect.left + (rect.width() - iconSizeInPixel) / 2
        val iconTop = rect.top + (rect.height() * ICON_VERTICAL_AREA_RATIO - iconSizeInPixel) / 2

        val drawable = ContextCompat.getDrawable(context, iconRes)
        drawable?.setTint(contentColor)
        drawable?.let { icon ->
            icon.setBounds(
                iconLeft.toInt(),
                iconTop.toInt(),
                (iconLeft + iconSizeInPixel).toInt(),
                (iconTop + iconSizeInPixel).toInt()
            )
            icon.draw(canvas)
        }

        paint.color = contentColor
        paint.textSize = textSizeInPixel
        paint.typeface = Typeface.DEFAULT
        paint.textAlign = Paint.Align.CENTER

        val titleBounds = Rect()
        paint.getTextBounds(title, 0, title.length, titleBounds)

        val textTop = iconTop + iconSizeInPixel + (verticalPadding / 2)
        val textY = textTop + titleBounds.height()
        canvas.drawText(title, rect.centerX(), textY, paint)

        clickableRegion = rect
    }

    fun handle(event: MotionEvent) {
        clickableRegion?.let {
            if (it.contains(event.x, event.y)) {
                clickListener()
            }
        }
    }

    companion object {
        const val ICON_VERTICAL_AREA_RATIO = 0.8f
    }
}

fun List<UnderlayButton>.intrinsicWidth(): Float {
    if (isEmpty()) return 0.0f
    return map { it.intrinsicWidth }.reduce { acc, fl -> acc + fl }
}
