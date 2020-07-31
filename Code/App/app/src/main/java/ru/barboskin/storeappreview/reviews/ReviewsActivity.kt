package ru.barboskin.storeappreview.reviews

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.activity_reviews_list.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.ListItem
import ru.barboskin.storeappreview.base.ui.ShimmerItem
import ru.barboskin.storeappreview.base.ui.getNewPagedItems
import ru.barboskin.storeappreview.base.ui.startLoadMore
import ru.barboskin.storeappreview.domain.model.CategoryItem
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.ext.*
import ru.barboskin.storeappreview.reviews.details.ReviewDetailsActivity

class ReviewsActivity : AppCompatActivity(R.layout.activity_reviews_list) {

    companion object {

        private const val EXTRA_CATEGORY_TYPE = "EXTRA_CATEGORY_TYPE"

        fun start(activity: Activity, categoryItem: CategoryItem) {
            activity.startActivity<ReviewsActivity> {
                putExtra(EXTRA_CATEGORY_TYPE, categoryItem)
            }
        }
    }

    private val repository by lazy { getComponent().reviewsRepository }
    private val categoryItem by lazy { intent.getSerializableExtra(EXTRA_CATEGORY_TYPE) as CategoryItem }
    private lateinit var adapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        loadReviews()
    }

    private fun initUi() {
        collapsingToolbar.title = categoryItem.type.name
        toolbar.initBackNavigation(this)
        adapter = ReviewAdapter(::onReviewClick, ::onLoadMore)
        recycler.adapter = adapter
    }

    private fun loadReviews() {
        adapter.submitList(createShimmers())
        updateReviewList(0)
    }

    private fun onReviewClick(reviewItem: ReviewItem) {
        ReviewDetailsActivity.start(this)
    }

    private fun onLoadMore(offset: Int) {
        adapter.startLoadMore()
        updateReviewList(offset)
    }

    private fun updateReviewList(offset: Int) {
        repository.getReviews(categoryItem.type, offset)
            .subscribeOn(io())
            .map(adapter::getNewPagedItems)
            .observeOn(mainThread())
            .subscribeBy(adapter::submitList)
            .disposeOnDestroy(this)
    }

    private fun createShimmers(): List<ListItem> {
        val shimmerItem = object : ShimmerItem {
            override val id: String = "SHIMMER_REVIEW"
            override val viewType: Int = R.layout.item_review_shimmer
        }
        return List(10) { shimmerItem }
    }
}
