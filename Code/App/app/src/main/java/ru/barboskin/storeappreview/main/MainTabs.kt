package ru.barboskin.storeappreview.main

import androidx.annotation.IdRes
import ru.barboskin.storeappreview.R

enum class MainTabs(@IdRes val id: Int) {
    REVIEWS(R.id.action_reviews),
    STATISTICS(R.id.action_statistics)
}
