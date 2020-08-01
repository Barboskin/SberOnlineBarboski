package ru.barboskin.storeappreview.base.network

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import ru.barboskin.storeappreview.domain.model.ChangeReviewTeamsBody
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.domain.model.TeamItem

interface ReviewsApi {

    @GET("api/commands/get_all")
    fun teams(): Single<List<TeamItem>>

    @GET("api/customer_reviews/category/{command_id}/get_batch")
    fun reviews(
        @Path("command_id") teamId: Int,
        @Query("offset") offset: Int
    ): Single<List<ReviewItem>>

    @POST("api/customer_reviews/change")
    fun changeTeams(
        @Body body: ChangeReviewTeamsBody
    ): Completable
}
