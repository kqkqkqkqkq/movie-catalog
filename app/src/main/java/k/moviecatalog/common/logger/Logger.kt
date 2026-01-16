package k.moviecatalog.common.logger

interface Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String)
}