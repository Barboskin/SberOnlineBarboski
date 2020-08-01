package ru.barboskin.storeappreview.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.JsonAdapter
import kotlinx.android.parcel.Parcelize
import ru.barboskin.storeappreview.base.network.DateJsonAdapter
import java.util.*

@Parcelize
@Keep
data class ReviewItem(
    val review_title: String,
    val review_text: String,
    @JsonAdapter(DateJsonAdapter::class)
    val create_time: Date,
    val rate: Int,
    val teams: List<TeamItem>,
    val intonation: Boolean,
    val platform: Boolean,
    val id: String
) : Parcelable
