package k.moviecatalog

import android.app.Application
import k.moviecatalog.di.AppComponent
import k.moviecatalog.di.AppComponentImpl

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = AppComponentImpl(this)
    }

    companion object {
        lateinit var instance: AppComponent
    }
}