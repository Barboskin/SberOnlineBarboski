package ru.barboskin.storeappreview.base.cache

import io.reactivex.Maybe
import ru.barboskin.storeappreview.domain.model.TeamItem

interface TeamsCache {

    fun getTeams(): Maybe<List<TeamItem>>

    fun updateTeams(teams: List<TeamItem>)
}
