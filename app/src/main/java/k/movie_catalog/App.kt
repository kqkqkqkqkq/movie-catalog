package k.movie_catalog

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import k.movie_catalog.di.AppComponent

class App : Application() {
    private val cicerone = Cicerone.create()
    val router
        get() = cicerone.router
    val navigatorHolder
        get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    val appComponent by lazy { AppComponent(router) }

    companion object {
        internal lateinit var INSTANCE: App
            private set
    }
}