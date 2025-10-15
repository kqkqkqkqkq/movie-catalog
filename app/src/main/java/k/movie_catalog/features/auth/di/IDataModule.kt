package k.movie_catalog.features.auth.di

import k.movie_catalog.repositories.IAuthRepository

interface IDataModule {
    val repository: IAuthRepository
}