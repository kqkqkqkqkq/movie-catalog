package k.movie_catalog.features.auth.di

import androidx.lifecycle.ViewModelProvider
import k.movie_catalog.features.auth.AuthViewModel

interface IFeatureModule {
    val viewModel: AuthViewModel
    val viewModelFactory: ViewModelProvider.Factory
}