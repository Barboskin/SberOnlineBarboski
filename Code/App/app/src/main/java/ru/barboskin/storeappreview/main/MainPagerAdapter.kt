package ru.barboskin.storeappreview.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.barboskin.storeappreview.statistics.StatisticsFragment
import ru.barboskin.storeappreview.teams.TeamsListFragment

class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    companion object {
        private const val REVIEWS_POSITION = 0
        private const val STATISTICS_POSITION = 1
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            REVIEWS_POSITION -> TeamsListFragment()
            STATISTICS_POSITION -> StatisticsFragment()
            else -> error("Non resolved fragment for position = $position")
        }
    }
}
