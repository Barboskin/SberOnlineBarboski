package ru.barboskin.storeappreview.base.ui

import androidx.recyclerview.widget.DiffUtil
import ru.barboskin.storeappreview.base.ui.items.ListItem

class DefaultDiffCallback<T : ListItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.equals(newItem)
    }
}
