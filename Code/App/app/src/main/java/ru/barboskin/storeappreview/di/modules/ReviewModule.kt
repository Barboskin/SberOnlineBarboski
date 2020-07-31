package ru.barboskin.storeappreview.di.modules

import ru.barboskin.storeappreview.domain.ReviewsRepository

interface ReviewModule {
    val reviewsRepository: ReviewsRepository
}

fun ReviewModule(networkModule: NetworkModule) = object : ReviewModule {

    override val reviewsRepository: ReviewsRepository = ReviewsRepository(
        networkModule.reviewsApi
    )
}
