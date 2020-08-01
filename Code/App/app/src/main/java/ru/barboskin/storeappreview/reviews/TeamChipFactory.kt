package ru.barboskin.storeappreview.reviews

import android.app.Activity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import ru.barboskin.storeappreview.ext.dpToPx


class TeamChipFactory : (Activity, String) -> View {

    override fun invoke(activity: Activity, team: String): View {
        return Chip(activity).apply {
            text = team
            val horizontalMargin = activity.dpToPx(8)
            val params = FlexboxLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setMargins(0, 0, horizontalMargin, 0)
            }
            layoutParams = params
        }
    }
}
