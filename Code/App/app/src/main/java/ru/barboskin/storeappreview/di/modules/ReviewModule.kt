package ru.barboskin.storeappreview.di.modules

import ru.barboskin.storeappreview.domain.ReviewsRepository
import ru.barboskin.storeappreview.reviews.edit.interactor.EditTeamsInteractor

interface ReviewModule {
    val reviewsRepository: ReviewsRepository
    val editTeamsInteractor: EditTeamsInteractor
}

fun ReviewModule(networkModule: NetworkModule) = object : ReviewModule {

    override val reviewsRepository: ReviewsRepository = ReviewsRepository(networkModule.reviewsApi)

    override val editTeamsInteractor: EditTeamsInteractor =
        EditTeamsInteractor(networkModule.reviewsApi)
}
