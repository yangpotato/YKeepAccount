package com.potato.ykeepaccount.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.commom.base.fragment.BaseFragment
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.main.presenter.AddAccountPresenter
import kotlinx.android.synthetic.main.fragment_add_account.*

class AddAccountFragment : BaseFragment<AddAccountPresenter>() {
    override fun loadData() {
        TODO("Not yet implemented")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_account;
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun createPresenter(): AddAccountPresenter {
        TODO("Not yet implemented")
    }

}