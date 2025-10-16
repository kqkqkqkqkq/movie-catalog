package k.movie_catalog

import android.app.Application
import k.movie_catalog.di.AppComponent
import k.movie_catalog.di.IAppComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponent(this)
    }

    companion object {
        lateinit var appComponent: IAppComponent
    }
}