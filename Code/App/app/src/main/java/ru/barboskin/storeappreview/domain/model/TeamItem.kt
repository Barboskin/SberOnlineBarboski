package ru.barboskin.storeappreview.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.items.ListItem

@Parcelize
data class TeamItem(
    val name: String,
    val decs: String,
    val count: Int,
    override val id: String
) : ListItem, Parcelable {
    override val viewType: Int = R.layout.item_team
}
