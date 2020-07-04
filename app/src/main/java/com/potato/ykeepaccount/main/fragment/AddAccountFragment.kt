package com.potato.ykeepaccount.main.fragment

import android.os.Bundle
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.utils.JumpUtil
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.main.presenter.AddAccountPresenter

class AddAccountFragment : BaseFragment<AddAccountPresenter>() {

    companion object{
        @JvmStatic
        fun newInstance() : BaseFragment<*> = AddAccountFragment()
    }

    override fun loadData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_account;
    }

    override fun initFragment(savedInstanceState: Bundle?) {

    }

    override fun createPresenter(): AddAccountPresenter {
       return AddAccountPresenter()
    }

}