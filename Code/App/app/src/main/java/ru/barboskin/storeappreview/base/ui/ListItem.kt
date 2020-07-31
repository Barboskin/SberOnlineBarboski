package ru.barboskin.storeappreview.base.ui

import androidx.annotation.LayoutRes

interface ListItem {
    val id: String

    @get:LayoutRes
    val viewType: Int
}
