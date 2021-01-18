package com.potato.ykeepaccount.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.potato.ykeepaccount.addaccount.fragment.AddAccountFragment
import com.potato.ykeepaccount.addaccount.fragment.CategoryListFragment
import com.potato.ykeepaccount.addaccount.popup.AddAccountPopup

class AddAccountPagerAdapter(fragmentActivity: FragmentActivity, var mTitles : Array<String>) : FragmentStateAdapter(fragmentActivity) {
    private var mFragmentList : MutableList<CategoryListFragment> = ArrayList()

    init {
        for((index, e) in mTitles.withIndex()){
            mFragmentList.add(CategoryListFragment.newInstance(index + 1))
        }
    }
    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    public fun getItem(position: Int) : CategoryListFragment{
        return mFragmentList[position]
    }
}