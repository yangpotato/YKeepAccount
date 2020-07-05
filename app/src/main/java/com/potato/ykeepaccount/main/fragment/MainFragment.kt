package com.potato.ykeepaccount.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.mvp.IBaseContract
import com.potato.ykeepaccount.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<IBaseContract.Presenter<*>>() {

    companion object {
        @JvmStatic
        fun newInstance() : BaseFragment<*> = MainFragment()
    }

    override fun loadData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initFragment(savedInstanceState: Bundle?) {

    }

    override fun createPresenter(): IBaseContract.Presenter<*>? {
        return null
    }
}