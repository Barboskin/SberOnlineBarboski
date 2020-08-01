package ru.barboskin.storeappreview.base.cache

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Maybe
import ru.barboskin.storeappreview.domain.model.CategoryItem

class CategoriesInMemoryCache : CategoriesCache {

    private val categoriesRelay = BehaviorRelay.create<List<CategoryItem>>()

    override fun getCategories(): Maybe<List<CategoryItem>> {
        return categoriesRelay.toMaybe()
    }

    override fun updateCategories(categories: List<CategoryItem>) {
        categoriesRelay.accept(categories)
    }

    private fun <T> BehaviorRelay<T>.toMaybe(): Maybe<T> {
        return value?.run { Maybe.just(this) } ?: Maybe.empty()
    }
}
