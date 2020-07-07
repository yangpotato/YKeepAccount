package com.potato.ykeepaccount.addaccount.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.base.commom.base.fragment.BaseFragment
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.main.presenter.AddAccountPresenter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.fragment_add_account.*

class AddAccountFragment : BaseFragment<AddAccountPresenter>() {
    private val mTagList = arrayOf("早餐", "晚餐", "中餐", "公交车", "地铁", "生活用品")

    companion object{
        @JvmStatic
        fun newInstance() : BaseFragment<*> =
            AddAccountFragment()
    }

    override fun loadData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_account;
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        flow_category.adapter = object : TagAdapter<String>(mTagList){
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val tv : TextView =
                    LayoutInflater.from(curActivity).inflate(R.layout.item_category, parent,
                        false) as TextView
                tv.text = t
                return tv
            }
        }
    }

    override fun createPresenter(): AddAccountPresenter {
       return AddAccountPresenter()
    }

}