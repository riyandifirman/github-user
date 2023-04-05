package com.riyandifirman.githubuser.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.riyandifirman.githubuser.R
import com.riyandifirman.githubuser.ui.detail.FollowersFragment
import com.riyandifirman.githubuser.ui.detail.FollowingFragment

class SectionsPagerAdapter(private val mContext: Context, fManager: FragmentManager, data: Bundle) : FragmentPagerAdapter(fManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // bundle untuk mengirim data ke fragment yang akan ditampilkan
    private var fragmentBundle: Bundle = data

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_a, R.string.tab_b)

    // fungsi untuk mengembalikan jumlah tab
    override fun getCount(): Int = TAB_TITLES.size

    // fungsi untuk mengembalikan fragment yang akan ditampilkan pada tab
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        // mengirim data ke fragment
        fragment?.arguments = fragmentBundle
        return fragment as Fragment
    }

    // fungsi untuk mengembalikan judul tab
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }
}