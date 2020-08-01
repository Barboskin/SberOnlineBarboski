package ru.barboskin.storeappreview.base.network

import io.reactivex.Single
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
                    id = it,
                    name = it,
                    decs = it + it + it + it,
                    count = random.nextInt(100)
                )
            }
        )
    }

    override fun reviews(team: String, offset: Int): Single<List<ReviewItem>> {
        val size = 50
        return Single.just(
            List(size) {
                ReviewItem(
                    title = "Заголовок отзыва ${it + offset}",
                    id = (it + offset).toString(),
                    date = Date(),
                    starCount = it % 5,
                    teams = listOf(team),
                    desc = "Описание того, как все очень плохо, как все бесит и того, как надо сделать, чтобы стало лучше, гораздо лучше, да хотя бы в миллион раз лучше. Спасибо ${it + offset}",
                    isNegative = it % 2 == 0,
                    isApple = it % 3 == 0
                )
            }
        )
    }
}
