package ru.barboskin.storeappreview.base.network

import io.reactivex.Single
import retrofit2.http.GET
import ru.barboskin.storeappreview.domain.model.CategoryItem
import ru.barboskin.storeappreview.domain.model.CategoryType
import ru.barboskin.storeappreview.domain.model.ReviewItem

interface ReviewsApi {

    @GET
    fun categories(): Single<List<CategoryItem>>

    @GET
    fun reviews(categoryType: CategoryType, offset: Int): Single<List<ReviewItem>>
}
