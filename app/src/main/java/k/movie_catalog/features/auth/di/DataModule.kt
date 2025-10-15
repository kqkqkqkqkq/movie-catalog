package k.movie_catalog.features.auth.di

import k.movie_catalog.repositories.AuthRepository
import k.movie_catalog.repositories.IAuthRepository


fun DataModule(dependencies: IAuthComponentDependencies): IDataModule = object : IDataModule {
    override val repository: IAuthRepository
        get() = AuthRepository(dependencies.authApi)
}