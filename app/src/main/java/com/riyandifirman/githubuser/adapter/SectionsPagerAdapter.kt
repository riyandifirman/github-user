package com.riyandifirman.githubuser.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.riyandifirman.githubuser.R
import com.riyandifirman.githubuser.ui.detail.FollowersFragment
import com.riyandifirman.githubuser.ui.detail.FollowingFragment

class SectionsPagerAdapter(private val mContext: Context, fManager: FragmentManager) : FragmentPagerAdapter(fManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_a, R.string.tab_b)

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }
}