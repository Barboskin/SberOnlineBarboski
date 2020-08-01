package ru.barboskin.storeappreview.base.cache

import io.reactivex.Maybe
import ru.barboskin.storeappreview.domain.model.CategoryItem

interface CategoriesCache {

    fun getCategories(): Maybe<List<CategoryItem>>

    fun updateCategories(categories: List<CategoryItem>)
}
