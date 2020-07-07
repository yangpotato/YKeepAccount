package com.potato.ykeepaccount.main.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.base.commom.base.fragment.BaseFragment
import com.potato.ykeepaccount.addaccount.fragment.AddAccountFragment
import com.potato.ykeepaccount.main.fragment.MainFragment

class MainPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    companion object{
        const val PAGE_ADD = 0;
        const val PAGE_MAIN = 1;
    }

    private var mFragments : SparseArray<BaseFragment<*>> = SparseArray()

    init {
        mFragments.put(PAGE_ADD, AddAccountFragment.newInstance())
        mFragments.put(PAGE_MAIN, MainFragment.newInstance())
    }

    override fun getItemCount(): Int {
        return mFragments.size()
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }

}