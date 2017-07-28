package com.mudio.movies.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.mudio.movies.Fragments.RecyclerFragment

class PagerAdapter (fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? { return RecyclerFragment.newInstance(position) }

    override fun getCount(): Int { return 2 }
}