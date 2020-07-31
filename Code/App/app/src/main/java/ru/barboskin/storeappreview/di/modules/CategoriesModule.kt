package ru.barboskin.storeappreview.di.modules

import ru.barboskin.storeappreview.base.cache.CategoriesInMemoryCache
import ru.barboskin.storeappreview.domain.CategoriesRepository

interface CategoriesModule {
    val categoriesRepository: CategoriesRepository
}

fun CategoriesModule(networkModule: NetworkModule) = object : CategoriesModule {

    override val categoriesRepository: CategoriesRepository = CategoriesRepository(
        networkModule.reviewsApi,
        CategoriesInMemoryCache()
    )

}
