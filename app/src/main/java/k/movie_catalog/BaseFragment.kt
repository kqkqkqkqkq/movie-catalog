package k.movie_catalog

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment : Fragment() {
    protected val app: App
        get() = App.INSTANCE

    protected val appComponent
        get() = app.appComponent

    protected inline fun <reified T : ViewModel> getViewModel(factory: ViewModelProvider.Factory): T =
        ViewModelProvider(this, factory)[T::class.java]
}