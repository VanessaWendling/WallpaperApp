package com.vamg.wallpaperapp.ui.pageadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    container: FragmentActivity,
    private val fragmentsList: List<Fragment>): FragmentStateAdapter(container) {
    override fun getItemCount(): Int = fragmentsList.size
    override fun createFragment(position: Int): Fragment = fragmentsList[position]
}