package ru.barboskin.storeappreview.teams

import android.view.View
import kotlinx.android.synthetic.main.item_team.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.BaseAdapter
import ru.barboskin.storeappreview.base.ui.BaseViewHolder
import ru.barboskin.storeappreview.base.ui.items.ErrorItemViewHolder
import ru.barboskin.storeappreview.base.ui.items.ListItem
import ru.barboskin.storeappreview.domain.model.TeamItem

class TeamsAdapter(
    private val teamClickListener: (TeamItem) -> Unit,
    private val onRepeatClick: () -> Unit
) : BaseAdapter<ListItem>() {

    override fun createViewHolder(viewType: Int, view: View): BaseViewHolder<ListItem> {
        return when (viewType) {
            R.layout.item_team -> TeamViewHolder(view, teamClickListener)
            R.layout.item_team_shimmer -> BaseViewHolder<ListItem>(view)
            R.layout.item_error -> ErrorItemViewHolder(view, onRepeatClick)
            else -> error("Unsupported viewType $viewType")
        } as BaseViewHolder<ListItem>
    }
}


class TeamViewHolder(
    view: View,
    private val teamClickListener: (TeamItem) -> Unit
) : BaseViewHolder<TeamItem>(view) {

    init {
        containerView.setOnClickListener { item?.let(teamClickListener) }
    }

    override fun bind(item: TeamItem) {
        super.bind(item)
        with(item) {
            titleView.text = name
            descView.text = decs
            countView.text = count.toString()
        }
    }
}
