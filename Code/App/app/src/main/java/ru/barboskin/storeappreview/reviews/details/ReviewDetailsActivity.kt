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
import ru.barboskin.storeappreview.ext.startActivity
import ru.barboskin.storeappreview.reviews.PlatformIconProvider
import ru.barboskin.storeappreview.reviews.TeamChipFactory
import ru.barboskin.storeappreview.reviews.edit.EXTRA_NEW_TEAMS
import ru.barboskin.storeappreview.reviews.edit.EditTeamsActivity
import kotlin.LazyThreadSafetyMode.NONE


class ReviewDetailsActivity : AppCompatActivity(R.layout.activity_review_details) {

    companion object {

        private const val EXTRA_REVIEW_ITEM = "EXTRA_REVIEW_ITEM"
        private const val EDIT_TEAMS_REQUEST_CODE = 54

        fun start(activity: Activity, sharedView: View, reviewItem: ReviewItem) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                sharedView,
                ViewCompat.getTransitionName(sharedView).orEmpty()
            )
            activity.startActivity<ReviewDetailsActivity>(options.toBundle()) {
                putExtra(EXTRA_REVIEW_ITEM, reviewItem)
            }
        }
    }

    private val reviewItem by lazy(NONE) { intent.getParcelableExtra(EXTRA_REVIEW_ITEM) as ReviewItem }
    private val teamChipFactory by lazy(NONE) { TeamChipFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_TEAMS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val newItems = data?.getSerializableExtra(EXTRA_NEW_TEAMS) as? List<String>
            newItems?.let(::showTeams)
        }
    }

    private fun initUi() {
        toolbar.initBackNavigation(this)
        with(reviewItem) {
            collapsingToolbar.title = date.formatAsString()
            platformIcon.setImageResource(PlatformIconProvider().invoke(isApple))
            ratingView.rating = starCount.toFloat()
            titleView.text = title
            descView.text = desc
            negativeIcon.isVisible = isNegative
            showTeams(teams)
            changeTeamsButton.setOnClickListener { onChangeTeamsClick() }
        }
    }

    private fun showTeams(teams: List<String>) {
        teamsContainer.removeAllViews()
        teams.forEach { team ->
            teamsContainer.addView(teamChipFactory(this@ReviewDetailsActivity, team))
        }
    }

    private fun onChangeTeamsClick() {
        EditTeamsActivity.startForResult(this, reviewItem, EDIT_TEAMS_REQUEST_CODE)
    }
}
