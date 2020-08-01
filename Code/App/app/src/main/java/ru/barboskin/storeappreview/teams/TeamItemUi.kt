package ru.barboskin.storeappreview.teams

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.items.ListItem
import ru.barboskin.storeappreview.domain.model.TeamItem

@Parcelize
data class TeamItemUi(
    val item: TeamItem
) : ListItem, Parcelable {
    override val viewType: Int = R.layout.item_team
    override val id: String = item.id.toString()
}
