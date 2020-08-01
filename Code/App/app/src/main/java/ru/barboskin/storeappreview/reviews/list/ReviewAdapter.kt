package ru.barboskin.storeappreview.reviews.list

import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.item_review.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.BaseAdapter
import ru.barboskin.storeappreview.base.ui.BaseViewHolder
import ru.barboskin.storeappreview.base.ui.items.DescriptionViewHolder
import ru.barboskin.storeappreview.base.ui.items.ErrorItemViewHolder
import ru.barboskin.storeappreview.base.ui.items.ListItem
import ru.barboskin.storeappreview.base.ui.items.PagedItemViewHolder
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.ext.formatAsString
import ru.barboskin.storeappreview.reviews.PlatformIconProvider

class ReviewAdapter(
    private val reviewClickListener: (View, ReviewItem) -> Unit,
    private val loadMoreCallback: (Int) -> Unit,
    private val onRepeatClick: () -> Unit,
    private val platformIconProvider: PlatformIconProvider = PlatformIconProvider()
) : BaseAdapter<ListItem>() {

    override fun createViewHolder(viewType: Int, view: View): BaseViewHolder<ListItem> {
        return when (viewType) {
            R.layout.item_review -> ReviewViewHolder(
                view,
                reviewClickListener,
                platformIconProvider
            )
            R.layout.item_paged_list -> PagedItemViewHolder(view, loadMoreCallback)
            R.layout.item_review_shimmer -> BaseViewHolder(view)
            R.layout.item_description -> DescriptionViewHolder(view)
            R.layout.item_error -> ErrorItemViewHolder(view, onRepeatClick)
            else -> error("Unsupported viewType $viewType")
        } as BaseViewHolder<ListItem>
    }
}

class ReviewViewHolder(
    view: View,
    private val reviewClickListener: (View, ReviewItem) -> Unit,
    private val platformIconProvider: PlatformIconProvider
) : BaseViewHolder<ReviewItemUi>(view) {

    init {
        containerView.setOnClickListener {
            item?.let {
                reviewClickListener(
                    platformIcon,
                    it.reviewItem
                )
            }
        }
    }

    override fun bind(item: ReviewItemUi) {
        super.bind(item)
        with(item.reviewItem) {
            titleView.text = review_title.takeIf { !it.startsWith("Без заголовка") } ?: review_text
            dateView.text = create_time.formatAsString()
            negativeIcon.isVisible = !intonation
            platformIcon.setImageResource(platformIconProvider(platform))
        }
    }
}
