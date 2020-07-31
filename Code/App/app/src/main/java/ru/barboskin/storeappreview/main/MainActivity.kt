package ru.barboskin.storeappreview.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.barboskin.storeappreview.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter =
            MainPagerAdapter(this)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            MainTabs.values()
                .firstOrNull { menuItem.itemId == it.id }
                ?.let {
                    viewPager.setCurrentItem(it.ordinal, false)
                    true
                } ?: false
        }
        viewPager.isUserInputEnabled = false
        bottomNavigation.isItemHorizontalTranslationEnabled = true
    }
}
