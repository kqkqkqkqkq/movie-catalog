package k.moviecatalog.common.extensions

import java.text.NumberFormat
import java.util.Locale

fun Int?.formatMoney(): String = this?.let {
    NumberFormat.getInstance(Locale.FRANCE).format(it)
} ?: "—"