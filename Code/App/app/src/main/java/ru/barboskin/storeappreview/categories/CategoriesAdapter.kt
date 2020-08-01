package ru.barboskin.storeappreview.categories

import android.view.View
import kotlinx.android.synthetic.main.item_category.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.BaseAdapter
import ru.barboskin.storeappreview.base.ui.BaseViewHolder
import ru.barboskin.storeappreview.base.ui.ListItem
import ru.barboskin.storeappreview.domain.model.CategoryItem

class CategoriesAdapter(
    private val categoryClickListener: (CategoryItem) -> Unit
) : BaseAdapter<ListItem>() {

    override fun createViewHolder(viewType: Int, view: View): BaseViewHolder<ListItem> {
        return when (viewType) {
            R.layout.item_category -> CategoriesViewHolder(view, categoryClickListener)
            else -> error("Unsupported viewType $viewType")
        } as BaseViewHolder<ListItem>
    }
}


class CategoriesViewHolder(
    view: View,
    private val categoryClickListener: (CategoryItem) -> Unit
) : BaseViewHolder<CategoryItem>(view) {

    init {
        containerView.setOnClickListener { item?.let(categoryClickListener) }
    }

    override fun bind(item: CategoryItem) {
        super.bind(item)
        with(item) {
            titleView.text = type.name
            countView.text = count.toString()
        }
    }
}
