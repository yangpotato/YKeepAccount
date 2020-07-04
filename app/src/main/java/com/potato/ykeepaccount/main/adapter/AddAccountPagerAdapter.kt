package com.potato.ykeepaccount.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.potato.ykeepaccount.main.fragment.AddAccountFragment

class AddAccountPagerAdapter(fragmentActivity: FragmentActivity, var mTitles : Array<String>) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return mTitles.size
    }

    override fun createFragment(position: Int): Fragment {
        return AddAccountFragment.newInstance()
    }
}