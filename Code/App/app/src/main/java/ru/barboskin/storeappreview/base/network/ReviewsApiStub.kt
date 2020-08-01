package ru.barboskin.storeappreview.base.network

import io.reactivex.Completable
import io.reactivex.Single
import ru.barboskin.storeappreview.domain.model.ChangeReviewTeamsBody
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.domain.model.TeamItem
import java.util.*

class ReviewsApiStub : ReviewsApi {

    override fun teams(): Single<List<TeamItem>> {
        val random = Random()
        return Single.just(
            listOf(
                "Платежи",
                "PR",
                "Валюта",
                "Зарплатный проект",
                "Кредиты",
                "Дизайн"
            ).map {
                TeamItem(
                    id = 2,
                    name = it,
                    description = it + it + it + it,
                    count = random.nextInt(100)
                )
            }
        )
    }

    override fun reviews(teamId: Int, offset: Int): Single<List<ReviewItem>> {
        val size = 50
        return Single.just(
            List(size) {
                ReviewItem(
                    review_title = "Заголовок отзыва ${it + offset}",
                    id = (it + offset).toString(),
                    create_time = Date(),
                    rate = it % 5,
                    teams = emptyList(),
                    review_text = "Описание того, как все очень плохо, как все бесит и того, как надо сделать, чтобы стало лучше, гораздо лучше, да хотя бы в миллион раз лучше. Спасибо ${it + offset}",
                    intonation = it % 2 == 0,
                    platform = it % 3 == 0
                )
            }
        )
    }

    override fun changeTeams(body: ChangeReviewTeamsBody): Completable {
        return Completable.complete()
    }
}
