package ru.barboskin.storeappreview.domain.model

import androidx.annotation.Keep

@Keep
data class ChangeReviewTeamsBody(
    val id: String,
    val teams: List<Int>
)
