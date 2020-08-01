package ru.barboskin.storeappreview.domain

import io.reactivex.Single
import ru.barboskin.storeappreview.base.cache.TeamsCache
import ru.barboskin.storeappreview.base.network.ReviewsApi
import ru.barboskin.storeappreview.domain.model.TeamItem

class CategoriesRepository(
    private val api: ReviewsApi,
    private val cache: TeamsCache
) {

    fun getTeams(): Single<List<TeamItem>> {
        return cache.getTeams()
            .switchIfEmpty(getFromRemote())
    }

    private fun getFromRemote(): Single<List<TeamItem>> {
        return api.teams()
            .doOnSuccess(cache::updateTeams)
    }
}
