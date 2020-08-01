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
                    title = "Review ${it + offset}",
                    id = (it + offset).toString(),
                    date = Date(),
                    starCount = it % 5,
                    teams = listOf(team),
                    desc = "Description on review ${it + offset}",
                    isNegative = it % 2 == 0,
                    isApple = it % 3 == 0
                )
            }
        )
    }
}
