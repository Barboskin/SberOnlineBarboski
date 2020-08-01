package ru.barboskin.storeappreview.base.network

import io.reactivex.Single
import retrofit2.http.GET
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.domain.model.TeamItem

interface ReviewsApi {

    @GET
    fun teams(): Single<List<TeamItem>>

    @GET
    fun reviews(team: String, offset: Int): Single<List<ReviewItem>>
}