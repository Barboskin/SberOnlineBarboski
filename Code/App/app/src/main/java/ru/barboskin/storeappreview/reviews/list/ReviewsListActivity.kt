package ru.barboskin.storeappreview.reviews.list

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.activity_reviews_list.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.items.*
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.domain.model.TeamItem
import ru.barboskin.storeappreview.ext.*
import ru.barboskin.storeappreview.reviews.details.ReviewDetailsActivity
import kotlin.LazyThreadSafetyMode.NONE

class ReviewsListActivity : AppCompatActivity(R.layout.activity_reviews_list) {

    companion object {

        private const val EXTRA_TEAM_ITEM = "EXTRA_TEAM_ITEM"

        fun start(activity: Activity, teamItem: TeamItem) {
            activity.startActivity<ReviewsListActivity> { putExtra(EXTRA_TEAM_ITEM, teamItem) }
        }
    }

    private val repository by lazy(NONE) { getComponent().reviewsRepository }
    private val teamItem by lazy(NONE) { intent.getParcelableExtra(EXTRA_TEAM_ITEM) as TeamItem }
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
            ::onLoadMore,
            ::onRepeatLoadClick
        )
        recycler.adapter = adapter
    }

    private fun loadReviews() {
        adapter.submitList(listOf(DescriptionItem(teamItem.description)) + createShimmers())
        updateReviewList(0)
    }

    private fun onReviewClick(sharedView: View, reviewItem: ReviewItem) {
        ReviewDetailsActivity.start(this, sharedView, reviewItem)
    }

    private fun onRepeatLoadClick() {
        adapter.startRepeatLoad()
        updateReviewList(0)
    }

    private fun onLoadMore(offset: Int) {
        adapter.startLoadMore()
        updateReviewList(offset)
    }

    private fun updateReviewList(offset: Int) {
        repository.getReviews(teamItem.id, offset)
            .subscribeOn(io())
            .map { it.map(::ReviewItemUi) }
            .map(adapter::getNewPagedItems)
            .onErrorReturn { onError(offset) }
            .observeOn(mainThread())
            .subscribeBy(adapter::submitList)
            .disposeOnDestroy(this)
    }

    private fun onError(offset: Int): List<ListItem> {
        return if (offset == 0) {
            adapter.currentList.filterIsInstance<DescriptionItem>() + listOf(ErrorItem(ErrorState.ERROR))
        } else {
            adapter.currentList.filterNot { it is PagedItem }
        }
    }

    private fun createShimmers(): List<ListItem> {
        val shimmerItem = object :
            ShimmerItem {
            override val id: String = "SHIMMER_REVIEW"
            override val viewType: Int = R.layout.item_review_shimmer
        }
        return List(10) { shimmerItem }
    }
}
