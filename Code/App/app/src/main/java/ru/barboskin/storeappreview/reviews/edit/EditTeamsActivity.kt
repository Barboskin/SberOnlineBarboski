package ru.barboskin.storeappreview.reviews.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.activity_edit_teams.*
import kotlinx.android.synthetic.main.item_full_screen_progress.*
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.base.ui.items.ListItem
import ru.barboskin.storeappreview.domain.model.ReviewItem
import ru.barboskin.storeappreview.domain.model.TeamItem
import ru.barboskin.storeappreview.ext.*
import ru.barboskin.storeappreview.reviews.TeamChipFactory
import java.io.Serializable
import kotlin.LazyThreadSafetyMode.NONE

const val EXTRA_NEW_TEAMS = "EXTRA_NEW_TEAMS"

class EditTeamsActivity : AppCompatActivity(R.layout.activity_edit_teams) {

    companion object {

        private const val EXTRA_REVIEW_ITEM = "EXTRA_REVIEW_ITEM"

        fun startForResult(activity: Activity, reviewItem: ReviewItem, requestCode: Int) {

            activity.startActivityForResult<EditTeamsActivity>(requestCode) {
                putExtra(EXTRA_REVIEW_ITEM, reviewItem)
            }
        }
    }

    private val reviewItem by lazy(NONE) { intent.getParcelableExtra(EXTRA_REVIEW_ITEM) as ReviewItem }
    private val teamChipFactory by lazy(NONE) { TeamChipFactory() }
    private val teamsRepository by lazy(NONE) { getComponent().teamsRepository }
    private val editTeamsInteractor by lazy(NONE) { getComponent().editTeamsInteractor }
    private lateinit var adapter: ChooseTeamsAdapter
    private val choosedItems = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {
        toolbar.initBackNavigation(this)
        choosedItems.addAll(reviewItem.teams)
        showCheckedItems()
        adapter = ChooseTeamsAdapter(::onCheckItems)
        recycler.adapter = adapter
        loadTeams()
        changeButton.setOnClickListener { onChangeTeamsClick() }
    }

    private fun onChangeTeamsClick() {
        showLoader()
        editTeamsInteractor.invoke(reviewItem, emptyList())
            .subscribeOn(io())
            .observeOn(mainThread())
            .doOnError { showErrorDialog() }
            .subscribeBy(::onSuccessChangedTeams)
            .disposeOnDestroy(this)
    }

    private fun onSuccessChangedTeams() {
        hideLoader()
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(EXTRA_NEW_TEAMS, choosedItems as Serializable)
        )
        finish()
    }

    private fun loadTeams() {
        teamsRepository.getTeams()
            .subscribeOn(io())
            .map(::mapTeams)
            .observeOn(mainThread())
            .subscribeBy(adapter::submitList)
            .disposeOnDestroy(this)
    }

    private fun showErrorDialog() {
        hideLoader()
        AlertDialog.Builder(this)
            .setTitle(R.string.error_dialog_title)
            .setMessage(R.string.error_dialog_message)
            .setPositiveButton(R.string.error_dialog_ok_button) { dialog, _ ->
                dialog.cancel()
            }.show().apply {
                getButton(BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(
                        this@EditTeamsActivity,
                        R.color.black
                    )
                )
            }
    }

    private fun showLoader() {
        progressOverlay.alpha = 0f
        progressOverlay.isVisible = true
        progressOverlay.animate()
            .alpha(1f)
            .setDuration(200)
            .start()
        changeButton.hide()
        setStatusBarColor(R.color.black_50)
    }

    private fun hideLoader() {
        progressOverlay.isVisible = false
        changeButton.show()
        setStatusBarColor(R.color.white)
    }

    private fun mapTeams(teams: List<TeamItem>): List<ListItem> {
        return teams.map {
            ChooseTeamItem(
                it.id,
                it.name,
                it.decs,
                choosedItems.contains(it.name)
            )
        }
    }

    private fun onCheckItems(item: ChooseTeamItem, isChecked: Boolean) {
        if (isChecked) {
            choosedItems.add(item.name)
            teamsContainer.addView(teamChipFactory(this@EditTeamsActivity, item.name))
        } else {
            choosedItems.remove(item.name)
            teamsContainer.removeView(teamsContainer.children.first { it.tag == item.name })
        }
        adapter.submitList(adapter.currentList.map {
            if (it == item && it is ChooseTeamItem) it.copy(
                isChecked = isChecked
            ) else it
        })
    }

    private fun showCheckedItems() {
        teamsContainer.removeAllViews()
        choosedItems.forEach { team ->
            teamsContainer.addView(teamChipFactory(this@EditTeamsActivity, team))
        }
    }
}
