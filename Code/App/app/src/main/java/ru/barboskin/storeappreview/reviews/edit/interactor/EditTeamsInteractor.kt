package ru.barboskin.storeappreview.reviews.edit.interactor

import io.reactivex.Completable
import io.reactivex.Single
import ru.barboskin.storeappreview.domain.ReviewsRepository
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.domain.model.TeamItem
import java.util.concurrent.TimeUnit

class EditTeamsInteractor(
    private val reviewsRepository: ReviewsRepository
) : (ReviewItem, List<TeamItem>) -> Completable {


    override fun invoke(reviewItem: ReviewItem, newTeams: List<TeamItem>): Completable {
        return Single.just(1)
            .delay(5, TimeUnit.SECONDS)
            // .map { 1/0 }
            .ignoreElement()
    }


}
