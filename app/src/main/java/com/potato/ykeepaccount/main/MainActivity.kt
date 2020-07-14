package com.potato.ykeepaccount.main

import android.os.Environment
import com.base.commom.base.activity.BaseActivity
import com.base.commom.mvp.IBaseContract
import com.base.commom.utils.LogUtil
import com.base.commom.utils.StatusBarUtil
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.main.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : BaseActivity<IBaseContract.Presenter<*>>() {
    override fun createPresenter(): IBaseContract.Presenter<IBaseContract.View>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initToolbar() {
        StatusBarUtil.darkMode(this)
    }

    override fun initActivity() {
        view_pager.adapter = MainPagerAdapter(this)
        view_pager.offscreenPageLimit = 2
        view_pager.setCurrentItem(1, false)

    }

}