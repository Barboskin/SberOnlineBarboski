package ru.barboskin.storeappreview.domain.model

import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.ListItem
import java.io.Serializable

data class CategoryItem(
    val type: CategoryType,
    val count: Int,
    override val id: String
) : ListItem, Serializable {
    override val viewType: Int = R.layout.item_category
}

enum class CategoryType : Serializable {
    PAYMENTS,
    SUPPORT,
    PR
}
