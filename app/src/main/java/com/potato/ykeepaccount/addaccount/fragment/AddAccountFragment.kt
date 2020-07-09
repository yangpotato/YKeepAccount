package com.potato.ykeepaccount.addaccount.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.utils.LogUtil
import com.potato.ykeepaccount.AccountApplication
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.addaccount.presenter.AddAccountPresenter
import com.potato.ykeepaccount.addaccount.presenter.IAddAccountContract
import com.potato.ykeepaccount.room.AccountDatabase
import com.potato.ykeepaccount.room.entity.AccountEntity
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.fragment_add_account.*

class AddAccountFragment : BaseFragment<AddAccountPresenter>(), IAddAccountContract.View {
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
        mPresenter.getDefaultCategoryList()
        btn_add.setOnClickListener { addAccount() }
    }

    private fun addAccount() {
        val accountEntity = AccountEntity(99.99, 1, 3)
        mPresenter.addAccount(accountEntity)
    }

    override fun createPresenter(): AddAccountPresenter {
       return AddAccountPresenter()
    }

    override fun showDefaultCategoryList(categoryList: List<CategoryEntity>?) {
        flow_category.adapter = object : TagAdapter<CategoryEntity>(categoryList){
            override fun getView(parent: FlowLayout?, position: Int, item: CategoryEntity?): View {
                val tv : TextView =
                    LayoutInflater.from(curActivity).inflate(R.layout.item_category, parent,
                        false) as TextView
                tv.text = item?.categoryName
                return tv
            }
        }
    }

    override fun addAccountSuccess(id: Long) {
        LogUtil.i("记账成功,id为$id")
    }

}