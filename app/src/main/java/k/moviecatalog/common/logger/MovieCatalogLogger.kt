package k.moviecatalog.common.logger

import android.util.Log

fun movieCatalogLogger() = object : Logger {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }
}