package ru.barboskin.storeappreview.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.frament_categories.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.domain.model.CategoryItem
import ru.barboskin.storeappreview.ext.disposeOnDestroy
import ru.barboskin.storeappreview.ext.getComponent
import ru.barboskin.storeappreview.ext.subscribeBy
import ru.barboskin.storeappreview.reviews.ReviewsActivity
import kotlin.LazyThreadSafetyMode.NONE

class CategoriesFragment : Fragment(R.layout.frament_categories) {

    private val repository by lazy(NONE) { getComponent().categoriesRepository }
    private lateinit var adapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoriesAdapter(::onCategoryClick)
        recycler.adapter = adapter
        loadCategories()
    }

    private fun loadCategories() {
        repository.getCategories()
            .subscribeOn(io())
            .observeOn(mainThread())
            .subscribeBy(adapter::submitList)
            .disposeOnDestroy(viewLifecycleOwner)
    }

    private fun onCategoryClick(categoryItem: CategoryItem) {
        ReviewsActivity.start(requireActivity(), categoryItem)
    }
}
