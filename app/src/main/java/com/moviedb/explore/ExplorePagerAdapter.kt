package com.moviedb.explore

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ExplorePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getPageTitle(position: Int): CharSequence? {
        super.getPageTitle(position)
        return when (position) {
            0 -> "TV"
            1 -> "Movie"
            else -> ""
        }
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = TVFragment()
            1 -> fragment = MovieFragment()
        }
        return fragment as Fragment
    }

    override fun getCount(): Int {
        return 2
    }
}