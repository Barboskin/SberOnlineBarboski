package ru.barboskin.storeappreview.base.ui.items

import androidx.annotation.LayoutRes

interface ListItem {
    val id: String

    @get:LayoutRes
    val viewType: Int
}
