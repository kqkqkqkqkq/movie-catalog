package k.movie_catalog

import android.app.Application
import k.movie_catalog.di.AppComponent
import k.movie_catalog.di.AppComponentImpl

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        app = AppComponentImpl(this)
    }

    companion object {
        lateinit var app: AppComponent
    }
}