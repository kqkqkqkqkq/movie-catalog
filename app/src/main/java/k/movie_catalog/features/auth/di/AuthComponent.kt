package k.movie_catalog.features.auth.di

fun AuthComponent(
    dependencies: IAuthComponentDependencies,
): IAuthComponent {
    val dataModule = DataModule(dependencies)
    return object : IAuthComponent, IDataModule by dataModule,
        IFeatureModule by FeatureModule(dataModule, dependencies) {}
}