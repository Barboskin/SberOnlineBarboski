package ru.barboskin.storeappreview.domain.model

import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.ListItem
import java.util.*

data class ReviewItem(
    val title: String,
    val desc: String,
    val date: Date,
    val starCount: Int,
    val categoryType: CategoryType,
    val isNegative: Boolean,
    override val id: String
) : ListItem {
    override val viewType: Int = R.layout.item_review
}
