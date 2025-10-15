package k.movie_catalog

import com.github.terrakok.cicerone.androidx.FragmentScreen
import k.movie_catalog.features.auth.login.LoginFragment
import k.movie_catalog.features.auth.register.RegisterFragment
import k.movie_catalog.features.collections.CollectionsFragment
import k.movie_catalog.features.main.MainFragment
import k.movie_catalog.features.profile.ProfileFragment

object Screens {
    fun Collections() = FragmentScreen("Collections") { CollectionsFragment.newInstance() }
    fun Profile() = FragmentScreen("Fragment") { ProfileFragment.newInstance() }
    fun Main() = FragmentScreen("Main") { MainFragment.newInstance() }
    fun Register() = FragmentScreen("Register") { RegisterFragment.newInstance() }
    fun Login() = FragmentScreen("Login") { LoginFragment.newInstance() }
}