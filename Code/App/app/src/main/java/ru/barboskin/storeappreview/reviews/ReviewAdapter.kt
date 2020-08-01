package ru.barboskin.storeappreview.reviews

import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.item_review.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.BaseAdapter
import ru.barboskin.storeappreview.base.ui.BaseViewHolder
import ru.barboskin.storeappreview.base.ui.ListItem
import ru.barboskin.storeappreview.base.ui.PagedItemViewHolder
import ru.barboskin.storeappreview.domain.model.ReviewItem
import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat("d MMMM y", Locale.getDefault())

class ReviewAdapter(
    private val reviewClickListener: (ReviewItem) -> Unit,
    private val loadMoreCallback: (Int) -> Unit
) : BaseAdapter<ListItem>() {

    override fun createViewHolder(viewType: Int, view: View): BaseViewHolder<ListItem> {
        return when (viewType) {
            R.layout.item_review -> ReviewViewHolder(view, reviewClickListener)
            R.layout.item_paged_list -> PagedItemViewHolder(view, loadMoreCallback)
            R.layout.item_review_shimmer -> BaseViewHolder(view)
            else -> error("Unsupported viewType $viewType")
        } as BaseViewHolder<ListItem>
    }
}

class ReviewViewHolder(
    view: View,
    private val reviewClickListener: (ReviewItem) -> Unit
) : BaseViewHolder<ReviewItem>(view) {

    init {
        containerView.setOnClickListener { item?.let(reviewClickListener) }
    }

    override fun bind(item: ReviewItem) {
        super.bind(item)
        with(item) {
            titleView.text = title
            dateView.text = dateFormat.format(date)
            negativeIcon.isVisible = isNegative
        }
    }
}
