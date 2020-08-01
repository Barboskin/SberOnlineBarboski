package ru.barboskin.storeappreview.domain.model

import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.ListItem
import java.io.Serializable

data class TeamItem(
    val name: String,
    val decs: String?,
    val count: Int,
    override val id: String
) : ListItem, Serializable {
    override val viewType: Int = R.layout.item_team
}
