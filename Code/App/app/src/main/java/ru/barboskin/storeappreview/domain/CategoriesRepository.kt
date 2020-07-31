package ru.barboskin.storeappreview.domain

import io.reactivex.Single
import ru.barboskin.storeappreview.base.cache.CategoriesCache
import ru.barboskin.storeappreview.base.network.ReviewsApi
import ru.barboskin.storeappreview.domain.model.CategoryItem

class CategoriesRepository(
    private val api: ReviewsApi,
    private val cache: CategoriesCache
) {

    fun getCategories(): Single<List<CategoryItem>> {
        return cache.getCategories()
            .switchIfEmpty(getFromRemote())
    }

    private fun getFromRemote(): Single<List<CategoryItem>> {
        return api.categories()
            .doOnSuccess(cache::updateCategories)
    }
}
