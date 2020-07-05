package com.potato.ykeepaccount.main

import androidx.viewpager2.widget.ViewPager2
import com.base.commom.base.activity.BaseActivity
import com.base.commom.mvp.BasePresenter
import com.base.commom.mvp.IBaseContract
import com.base.commom.utils.LogUtil
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.main.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<IBaseContract.Presenter<*>>() {
    override fun createPresenter(): IBaseContract.Presenter<IBaseContract.View>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initToolbar() {

    }

    override fun initActivity() {
        view_pager.adapter = MainPagerAdapter(this)
        view_pager.offscreenPageLimit = 2
        view_pager.setCurrentItem(1, false)
    }

}