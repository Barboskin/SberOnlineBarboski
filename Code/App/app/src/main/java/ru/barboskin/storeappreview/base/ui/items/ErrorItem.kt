package ru.barboskin.storeappreview.base.ui.items

import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.item_error.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.BaseAdapter
import ru.barboskin.storeappreview.base.ui.BaseViewHolder
import ru.barboskin.storeappreview.base.ui.items.ErrorState.LOADING

data class ErrorItem(
    val state: ErrorState
) : ListItem {
    override val id: String = "ERROR_ITEM"
    override val viewType: Int = R.layout.item_error
}

class ErrorItemViewHolder(
    view: View,
    onRepeatClick: () -> Unit
) : BaseViewHolder<ErrorItem>(view) {

    init {
        repeatButton.setOnClickListener { if (item?.state != LOADING) onRepeatClick() }
    }

    override fun bind(item: ErrorItem) {
        super.bind(item)
        errorBlock.isVisible = item.state == ErrorState.ERROR
        progressBar.isVisible = item.state == LOADING
    }
}

enum class ErrorState {
    ERROR,
    LOADING
}

fun BaseAdapter<ListItem>.startRepeatLoad() {
    submitList(currentList.map { if (it is ErrorItem) ErrorItem(LOADING) else it })
}
