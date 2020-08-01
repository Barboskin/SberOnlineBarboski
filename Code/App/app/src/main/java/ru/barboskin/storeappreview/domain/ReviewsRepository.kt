package ru.barboskin.storeappreview.domain

import io.reactivex.Single
import ru.barboskin.storeappreview.base.network.ReviewsApi
import ru.barboskin.storeappreview.domain.model.ReviewItem

class ReviewsRepository(
    private val api: ReviewsApi
) {

    fun getReviews(teamId: Int, offset: Int): Single<List<ReviewItem>> {
        return api.reviews(teamId, offset)
    }
}
