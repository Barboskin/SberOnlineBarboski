package ru.barboskin.storeappreview.domain.model

import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.items.ListItem
import java.util.*

data class ReviewItem(
    val title: String,
    val desc: String,
    val date: Date,
    val starCount: Int,
    val teams: List<String>,
    val isNegative: Boolean,
    val isApple: Boolean,
    override val id: String
) : ListItem {
    override val viewType: Int = R.layout.item_review
}
