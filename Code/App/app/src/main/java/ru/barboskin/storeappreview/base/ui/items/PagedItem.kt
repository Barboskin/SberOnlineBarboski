package ru.barboskin.storeappreview.base.ui.items

import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.item_paged_list.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.BaseAdapter
import ru.barboskin.storeappreview.base.ui.BaseViewHolder

class PagedItem(
    val state: PagedState
) : ListItem {
    override val id: String = "PagedItem"
    override val viewType: Int = R.layout.item_paged_list
}

class PagedItemViewHolder(
    view: View,
    private val loadMoreCallback: (Int) -> Unit
) : BaseViewHolder<PagedItem>(view) {

    override fun bind(item: PagedItem) {
        super.bind(item)
        when (item.state) {
            PagedState.EMPTY -> {
                progressView.isVisible = false
            }
            PagedState.IDLE -> {
                loadMoreCallback(adapterPosition)
            }
            PagedState.ERROR -> {
            }
            PagedState.LOADING -> {
                progressView.isVisible = true
            }
        }
    }
}

enum class PagedState {
    LOADING,
    IDLE,
    ERROR,
    EMPTY
}

fun BaseAdapter<ListItem>.startLoadMore() {
    submitList(currentList.map {
        if (it is PagedItem) PagedItem(
            PagedState.LOADING
        ) else it
    })
}

fun BaseAdapter<ListItem>.getNewPagedItems(newItems: List<ListItem>): List<ListItem> {
    return currentList.filterNot { it is PagedItem || it is ShimmerItem } + newItems + listOf(
        PagedItem(PagedState.IDLE)
    )
}
