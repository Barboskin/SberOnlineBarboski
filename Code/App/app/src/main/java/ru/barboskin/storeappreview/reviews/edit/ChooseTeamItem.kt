package ru.barboskin.storeappreview.reviews.edit

import android.view.View
import kotlinx.android.synthetic.main.item_team_choose.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.BaseViewHolder
import ru.barboskin.storeappreview.base.ui.items.ListItem

data class ChooseTeamItem(
    override val id: String,
    val name: String,
    val desc: String,
    val isChecked: Boolean
) : ListItem {
    override val viewType: Int = R.layout.item_team_choose
}

class ChooseTeamItemViewHolder(
    view: View,
    private val checkedStateListener: (ChooseTeamItem, Boolean) -> Unit
) : BaseViewHolder<ChooseTeamItem>(view) {

    init {
        view.setOnClickListener { checkbox.performClick() }
    }

    override fun bind(item: ChooseTeamItem) {
        super.bind(item)
        checkbox.setOnCheckedChangeListener(null)
        with(item) {
            titleView.text = name
            descView.text = desc
            checkbox.isChecked = isChecked
        }
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            checkedStateListener(
                item,
                isChecked
            )
        }
    }
}
