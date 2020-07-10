package com.potato.ykeepaccount.addaccount.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.utils.JumpUtil
import com.base.commom.utils.LogUtil
import com.lxj.xpopup.XPopup
import com.potato.ykeepaccount.AccountApplication
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.addaccount.presenter.AddAccountPresenter
import com.potato.ykeepaccount.addaccount.presenter.IAddAccountContract
import com.potato.ykeepaccount.model.LabelModel
import com.potato.ykeepaccount.room.AccountDatabase
import com.potato.ykeepaccount.room.entity.AccountEntity
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.TypeEntity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_account.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddAccountFragment : BaseFragment<AddAccountPresenter>(), IAddAccountContract.View {
    //分类标签
    private var mCategoryId : Long = -1
    private var mCategoryList : List<CategoryEntity>? = ArrayList()
    private val mLabelList : MutableList<LabelModel> = ArrayList()
    private var mPrimaryTypeId : Int = -1
    private var mTypeList : List<TypeEntity> = ArrayList()

    companion object{
        @JvmStatic
        fun newInstance(id : Int) : BaseFragment<*> = AddAccountFragment().apply {
            arguments = Bundle().apply {
                putInt(JumpUtil.P1, id)
            }
        }
    }

    override fun loadData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_account;
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        mPrimaryTypeId = arguments?.getInt(JumpUtil.P1)!!
        mPresenter.getDefaultCategoryList()
        initLabelList()
        btn_add.setOnClickListener { addAccount() }
        flow_category.setOnTagClickListener { view, position, parent ->
            mCategoryId = mCategoryList?.get(position)?.id!!
            true
        }
    }

    private fun initLabelList() {
        mLabelList.add(LabelModel(LabelModel.TYPE, "支付方式"))
        mLabelList.add(LabelModel(LabelModel.TIME, SimpleDateFormat.getDateInstance().format(System.currentTimeMillis())))
        flow_label.adapter = object : TagAdapter<LabelModel>(mLabelList){
            override fun getView(parent: FlowLayout?, position: Int, item: LabelModel?): View {
                val tv : TextView =
                    LayoutInflater.from(curActivity).inflate(R.layout.item_category, parent,
                        false) as TextView
                tv.text = item?.name
                return tv
            }
        }
        flow_label.setOnTagClickListener(object : TagFlowLayout.OnTagClickListener{
            override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
                if(position == 0){
                    if(mTypeList.isEmpty())
                        mPresenter.getTypeListByPrimaryId(mPrimaryTypeId.toLong())
                }
            }

        })
    }

    private fun addAccount() {
        if(edt_money.text.toString().trim().isEmpty()){
            showMessage("请输入金额")
            return
        }
        val money = edt_money.text.toString().trim().toDouble()
        if(money <= 0){
            showMessage("请输入金额")
            return
        }
        if(mCategoryId == -1L){
            showMessage("请选择标签")
            return
        }
        val accountEntity = AccountEntity(money, 1, mCategoryId, edt_remark.text.toString().trim())
        mPresenter.addAccount(accountEntity)
    }

    override fun createPresenter(): AddAccountPresenter {
       return AddAccountPresenter()
    }

    override fun showDefaultCategoryList(categoryList: List<CategoryEntity>?) {
        this.mCategoryList = categoryList
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

    override fun showPrimaryTypeList(typeList: List<TypeEntity>) {

    }

    override fun showTypeListByPrimaryId(typeList: List<TypeEntity>) {
        val mList : MutableList<String> = ArrayList()
//        val mList : Array<String> = Array()
        Observable.fromIterable(typeList).map { t -> t.name }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<String>{
            override fun onComplete() {
               XPopup.Builder(curActivity).asAttachList(mList.toTypedArray(), intArrayOf(), null).show()
            }

            override fun onSubscribe(d: Disposable) {
                TODO("Not yet implemented")
            }

            override fun onNext(t: String) {
                mList.add(t)
            }

            override fun onError(e: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}

