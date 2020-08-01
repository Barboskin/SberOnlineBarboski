package ru.barboskin.storeappreview.reviews.edit.interactor

import io.reactivex.Completable
import ru.barboskin.storeappreview.base.network.ReviewsApi
import ru.barboskin.storeappreview.domain.model.ChangeReviewTeamsBody
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.domain.model.TeamItem

class EditTeamsInteractor(
    private val api: ReviewsApi
) : (ReviewItem, List<TeamItem>) -> Completable {

    override fun invoke(reviewItem: ReviewItem, newTeams: List<TeamItem>): Completable {
        val body = ChangeReviewTeamsBody(reviewItem.id, newTeams.map(TeamItem::id))
        return api.changeTeams(body)
    }
}
