package k.moviecatalog.utils.ui

import android.graphics.Color

object ColorConverter {
    fun calculateColor(
        rating: Double,
        lowColor: Int,
        midColor: Int,
        highColor: Int,
        minRating: Double = 0.0,
        maxRating: Double = 10.0,
    ): Int {
        val normalized = ((rating - minRating) / (maxRating - minRating))
            .coerceIn(0.0, 1.0)

        return when {
            normalized <= 0.5 -> {
                val fraction = (normalized / 0.5).toFloat()
                blendColors(lowColor, midColor, fraction)
            }
            else -> {
                val fraction = ((normalized - 0.5) / 0.5).toFloat()
                blendColors(midColor, highColor, fraction)
            }
        }
    }

    private fun blendColors(startColor: Int, endColor: Int, fraction: Float): Int {
        val startA = Color.alpha(startColor)
        val startR = Color.red(startColor)
        val startG = Color.green(startColor)
        val startB = Color.blue(startColor)

        val endA = Color.alpha(endColor)
        val endR = Color.red(endColor)
        val endG = Color.green(endColor)
        val endB = Color.blue(endColor)

        return Color.argb(
            (startA + (endA - startA) * fraction).toInt(),
            (startR + (endR - startR) * fraction).toInt(),
            (startG + (endG - startG) * fraction).toInt(),
            (startB + (endB - startB) * fraction).toInt()
        )
    }
}