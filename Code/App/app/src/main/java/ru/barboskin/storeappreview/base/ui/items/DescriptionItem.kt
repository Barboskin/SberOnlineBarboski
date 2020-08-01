package ru.barboskin.storeappreview.base.ui.items

import android.view.View
import android.widget.TextView
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.BaseViewHolder

data class DescriptionItem(
    val text: String
) : ListItem {
    override val viewType: Int = R.layout.item_description
    override val id: String = text
}

class DescriptionViewHolder(
    view: View
) : BaseViewHolder<DescriptionItem>(view) {

    override fun bind(item: DescriptionItem) {
        super.bind(item)
        (containerView as? TextView)?.text = item.text
    }
}
