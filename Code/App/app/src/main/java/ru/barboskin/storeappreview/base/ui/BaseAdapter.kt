package ru.barboskin.storeappreview.base.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.barboskin.storeappreview.base.ui.items.ListItem

abstract class BaseAdapter<T : ListItem> :
    ListAdapter<T, BaseViewHolder<T>>(DefaultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return createViewHolder(viewType, view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    abstract fun createViewHolder(viewType: Int, view: View): BaseViewHolder<T>
}
