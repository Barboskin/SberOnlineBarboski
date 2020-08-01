package ru.barboskin.storeappreview.reviews.edit

import android.view.View
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.BaseAdapter
import ru.barboskin.storeappreview.base.ui.BaseViewHolder
import ru.barboskin.storeappreview.base.ui.items.ListItem

class ChooseTeamsAdapter(
    private val checkedStateListener: (ChooseTeamItem, Boolean) -> Unit
) : BaseAdapter<ListItem>() {

    override fun createViewHolder(viewType: Int, view: View): BaseViewHolder<ListItem> {
        return when (viewType) {
            R.layout.item_team_choose -> ChooseTeamItemViewHolder(view, checkedStateListener)
            else -> error("Unsupported viewType $viewType")
        } as BaseViewHolder<ListItem>
    }
}
