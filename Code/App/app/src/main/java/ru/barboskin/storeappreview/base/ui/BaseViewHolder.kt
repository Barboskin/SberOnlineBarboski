package ru.barboskin.storeappreview.base.ui

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class BaseViewHolder<T>(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    protected var item: T? = null
        private set

    @CallSuper
    open fun bind(item: T) {
        this.item = item
    }
}
