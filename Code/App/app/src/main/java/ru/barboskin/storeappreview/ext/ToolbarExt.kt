package ru.barboskin.storeappreview.ext

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import ru.barboskin.storeappreview.R

fun Toolbar.initBackNavigation(activity: Activity) {
    setNavigationIcon(R.drawable.ic_arrow_back)
    setNavigationOnClickListener { activity.onBackPressed() }
}
