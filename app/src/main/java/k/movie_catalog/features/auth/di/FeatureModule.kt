package k.movie_catalog.features.auth.di

import androidx.lifecycle.ViewModelProvider
import k.movie_catalog.di.ViewModelFactory
import k.movie_catalog.features.auth.AuthViewModel


fun FeatureModule(
    dataModule: IDataModule,
    dependencies: IAuthComponentDependencies,
): IFeatureModule =
    object : IFeatureModule {
        override val viewModel: AuthViewModel
            get() = AuthViewModel(dependencies.router, dataModule.repository)
        override val viewModelFactory: ViewModelProvider.Factory
            get() = ViewModelFactory { viewModel }
    }