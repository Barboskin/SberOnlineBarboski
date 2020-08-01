package ru.barboskin.storeappreview.reviews.list

import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.items.ListItem
import ru.barboskin.storeappreview.domain.model.ReviewItem

data class ReviewItemUi(
    val reviewItem: ReviewItem
) : ListItem {
    override val id: String = reviewItem.id
    override val viewType: Int = R.layout.item_review
}
