package ru.barboskin.storeappreview.teams

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.frament_teams_list.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.items.ListItem
import ru.barboskin.storeappreview.base.ui.items.ShimmerItem
import ru.barboskin.storeappreview.domain.model.TeamItem
import ru.barboskin.storeappreview.ext.disposeOnDestroy
import ru.barboskin.storeappreview.ext.getComponent
import ru.barboskin.storeappreview.ext.subscribeBy
import ru.barboskin.storeappreview.reviews.list.ReviewsListActivity
import java.util.concurrent.TimeUnit
import kotlin.LazyThreadSafetyMode.NONE

class TeamsListFragment : Fragment(R.layout.frament_teams_list) {

    private val repository by lazy(NONE) { getComponent().categoriesRepository }
    private lateinit var adapter: TeamsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TeamsAdapter(::onTeamClick)
        recycler.adapter = adapter
        loadCategories()
    }

    private fun loadCategories() {
        adapter.submitList(createShimmers())
        repository.getTeams()
            .subscribeOn(io())
            .delay(2, TimeUnit.SECONDS)
            .observeOn(mainThread())
            .subscribeBy(adapter::submitList)
            .disposeOnDestroy(viewLifecycleOwner)
    }

    private fun onTeamClick(teamItem: TeamItem) {
        ReviewsListActivity.start(requireActivity(), teamItem)
    }

    private fun createShimmers(): List<ListItem> {
        val shimmerItem = object :
            ShimmerItem {
            override val id: String = "SHIMMER_REVIEW"
            override val viewType: Int = R.layout.item_team_shimmer
        }
        return List(10) { shimmerItem }
    }
}
