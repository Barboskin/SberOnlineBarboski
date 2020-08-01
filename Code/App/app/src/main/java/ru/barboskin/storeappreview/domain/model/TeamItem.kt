package ru.barboskin.storeappreview.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class TeamItem(
    val name: String,
    val description: String,
    val count: Int?,
    val id: Int
) : Parcelable
