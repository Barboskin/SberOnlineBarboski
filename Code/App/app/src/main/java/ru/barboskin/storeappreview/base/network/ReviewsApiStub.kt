package ru.barboskin.storeappreview.base.network

import io.reactivex.Single
import ru.barboskin.storeappreview.domain.model.CategoryItem
import ru.barboskin.storeappreview.domain.model.CategoryType
import ru.barboskin.storeappreview.domain.model.ReviewItem
import java.util.*

class ReviewsApiStub : ReviewsApi {

    override fun categories(): Single<List<CategoryItem>> {
        return Single.just(
            CategoryType.values().map {
                CategoryItem(
                    type = it,
                    count = it.ordinal,
                    id = it.name
                )
            }
        )
    }

    override fun reviews(categoryType: CategoryType, offset: Int): Single<List<ReviewItem>> {
        val size = 50
        return Single.just(
            List(size) {
                ReviewItem(
                    title = "Review ${it + offset}",
                    id = (it + offset).toString(),
                    date = Date(),
                    starCount = it % 5,
                    categoryType = categoryType,
                    desc = "Description on review ${it + offset}",
                    isNegative = it % 2 == 0
                )
            }
        )
    }
}
