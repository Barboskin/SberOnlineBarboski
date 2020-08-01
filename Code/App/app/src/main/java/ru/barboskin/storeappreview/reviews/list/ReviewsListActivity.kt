package ru.barboskin.storeappreview.reviews.list

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
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.domain.model.TeamItem
import ru.barboskin.storeappreview.ext.*
import ru.barboskin.storeappreview.reviews.details.ReviewDetailsActivity
import java.util.concurrent.TimeUnit

class ReviewsListActivity : AppCompatActivity(R.layout.activity_reviews_list) {

    companion object {

        private const val EXTRA_TEAM_ITEM = "EXTRA_TEAM_ITEM"

        fun start(activity: Activity, teamItem: TeamItem) {
            activity.startActivity<ReviewsListActivity> {
                putExtra(EXTRA_TEAM_ITEM, teamItem)
            }
        }
    }

    private val repository by lazy { getComponent().reviewsRepository }
    private val teamItem by lazy { intent.getSerializableExtra(EXTRA_TEAM_ITEM) as TeamItem }
    private lateinit var adapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        loadReviews()
    }

    private fun initUi() {
        collapsingToolbar.title = teamItem.name
        toolbar.initBackNavigation(this)
        adapter = ReviewAdapter(
            ::onReviewClick,
            ::onLoadMore
        )
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
        repository.getReviews(teamItem.name, offset)
            .subscribeOn(io())
            .map(adapter::getNewPagedItems)
            .delay(2, TimeUnit.SECONDS)
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
