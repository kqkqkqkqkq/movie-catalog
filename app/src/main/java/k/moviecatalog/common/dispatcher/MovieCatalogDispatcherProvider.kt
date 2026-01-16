package k.moviecatalog.common.dispatcher

import kotlinx.coroutines.Dispatchers

class MovieCatalogDispatcherProvider : DispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}