package ru.barboskin.storeappreview.base.cache

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Maybe
import ru.barboskin.storeappreview.domain.model.TeamItem

class TeamsInMemoryCache : TeamsCache {

    private val categoriesRelay = BehaviorRelay.create<List<TeamItem>>()

    override fun getTeams(): Maybe<List<TeamItem>> {
        return categoriesRelay.toMaybe()
    }

    override fun updateTeams(teams: List<TeamItem>) {
        categoriesRelay.accept(teams)
    }

    private fun <T> BehaviorRelay<T>.toMaybe(): Maybe<T> {
        return value?.run { Maybe.just(this) } ?: Maybe.empty()
    }
}
