package com.potato.ykeepaccount.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.potato.ykeepaccount.addaccount.fragment.CategoryListFragment

class AddAccountPagerAdapter(fragmentActivity: FragmentActivity, var mTitles : Array<String>) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return mTitles.size
    }

    override fun createFragment(position: Int): Fragment {
        return CategoryListFragment.newInstance(position + 1)
    }
}