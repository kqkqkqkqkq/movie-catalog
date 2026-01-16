package k.moviecatalog.utils.ui

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun dateToString(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return date.format(formatter)
    }
}
