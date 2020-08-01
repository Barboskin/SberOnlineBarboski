package ru.barboskin.storeappreview.reviews.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_review_details.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.ext.formatAsString
import ru.barboskin.storeappreview.ext.initBackNavigation
import ru.barboskin.storeappreview.reviews.PlatformIconProvider
import ru.barboskin.storeappreview.reviews.TeamChipFactory
import kotlin.LazyThreadSafetyMode.NONE


class ReviewDetailsActivity : AppCompatActivity(R.layout.activity_review_details) {

    companion object {

        private const val EXTRA_REVIEW_ITEM = "EXTRA_REVIEW_ITEM"

        fun start(
            activity: Activity,
            sharedView: View,
            reviewItem: ReviewItem
        ) {
            val intent = Intent(activity, ReviewDetailsActivity::class.java).putExtra(
                EXTRA_REVIEW_ITEM,
                reviewItem
            )
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                sharedView,
                ViewCompat.getTransitionName(sharedView).orEmpty()
            )
            activity.startActivity(intent, options.toBundle())
        }
    }

    private val reviewItem by lazy(NONE) { intent.getParcelableExtra(EXTRA_REVIEW_ITEM) as ReviewItem }
    private val teamChipFactory by lazy(NONE) { TeamChipFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.initBackNavigation(this)
        with(reviewItem) {
            collapsingToolbar.title = date.formatAsString()
            platformIcon.setImageResource(PlatformIconProvider().invoke(isApple))
            ratingView.rating = starCount.toFloat()
            titleView.text = title
            descView.text = desc
            negativeIcon.isVisible = isNegative
            /*teams.forEach { team ->
                teamsContainer.addView(teamChipFactory(this@ReviewDetailsActivity, team))
            }*/

            List(20) { "Команда $it" }.forEach { team ->
                teamsContainer.addView(teamChipFactory(this@ReviewDetailsActivity, team))
            }
            changeTeamsButton.setOnClickListener { }
        }
    }
}
