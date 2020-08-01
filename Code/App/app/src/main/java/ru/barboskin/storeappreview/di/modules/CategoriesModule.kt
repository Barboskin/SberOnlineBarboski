package ru.barboskin.storeappreview.di.modules

import ru.barboskin.storeappreview.base.cache.TeamsInMemoryCache
import ru.barboskin.storeappreview.domain.TeamsRepository

interface TeamsModule {
    val teamsRepository: TeamsRepository
}

fun TeamsModule(networkModule: NetworkModule) = object : TeamsModule {

    override val teamsRepository: TeamsRepository = TeamsRepository(
        networkModule.reviewsApi,
        TeamsInMemoryCache()
    )

}
