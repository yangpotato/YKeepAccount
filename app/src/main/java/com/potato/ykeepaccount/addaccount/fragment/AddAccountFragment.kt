package com.potato.ykeepaccount.addaccount.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.utils.JumpUtil
import com.base.commom.utils.LogUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lxj.xpopup.XPopup
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.potato.ykeepaccount.AccountApplication
import com.potato.ykeepaccount.Constant
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.addaccount.popup.TypePopup
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
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddAccountFragment : BaseFragment<AddAccountPresenter>(), IAddAccountContract.View {
    //分类标签
    private var mCategoryId : Long = -1
    private var mTypeId : Long = -1
    private var mCostTime : Long = 0
    private var mCategoryList : List<CategoryEntity>? = ArrayList()
    private val mLabelList : MutableList<LabelModel> = ArrayList()
    private var mPrimaryTypeId : Int = -1
    private var mTypeList : List<TypeEntity> = ArrayList()
    private lateinit var mTypePopup : TypePopup

    companion object{
        @JvmStatic
        fun newInstance(primaryTypeId : Int, categoryId: Long) : AddAccountFragment = AddAccountFragment().apply {
            arguments = Bundle().apply {
                putInt(JumpUtil.P1, primaryTypeId)
                putLong(JumpUtil.P2, categoryId)
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
        mCategoryId = arguments?.getLong(JumpUtil.P2)!!

        if(mPrimaryTypeId == 1)
            mPresenter.getDefaultCategoryList()
        initLabelList()
        btn_add.setOnClickListener { addAccount() }
        flow_category.setOnTagClickListener { view, position, parent ->
            mCategoryId = mCategoryList?.get(position)?.categoryId!!
            true
        }
    }

    private fun initLabelList() {
        mCostTime = System.currentTimeMillis()
        tv_time.text = SimpleDateFormat.getDateInstance().format(System.currentTimeMillis())
        tv_pay_way.setOnClickListener {
            if(mTypeList.isEmpty())
                mPresenter.getTypeListByPrimaryId(mPrimaryTypeId.toLong())
        }
        tv_time.setOnClickListener {
            val timePopup = TimePickerPopup(curActivity)
                .setDefaultDate(Calendar.getInstance())
                .setTimePickerListener(object : TimePickerListener{
                    override fun onTimeConfirm(date: Date?, view: View?) {
                        tv_time.text = SimpleDateFormat.getDateInstance().format(date?:System.currentTimeMillis())
                        mCostTime = date?.time?:0
                    }

                    override fun onTimeChanged(date: Date?) {

                    }
                })
            XPopup.Builder(curActivity).asCustom(timePopup).show()
        }
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
        if(mCategoryId == -1L && mPrimaryTypeId != 2){
            showMessage("请选择标签")
            return
        }
        if(mTypeId == -1L){
            showMessage("请选择支付方式")
            return
        }
        val accountEntity = AccountEntity(BigDecimal(money), mTypeId, mCategoryId, edt_remark.text.toString().trim(), "", mCostTime)
        mPresenter.addAccount(accountEntity)
    }

    override fun createPresenter(): AddAccountPresenter {
       return AddAccountPresenter()
    }

    override fun showDefaultCategoryList(categoryList: MutableList<CategoryEntity>?) {
        LogUtil.i("categoryList: $categoryList")
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
        Constant.isUpdateAccount = true
        LogUtil.i("记账成功,id为$id")
        if(id > 0) {
            showMessage("添加成功")

        }else
            showMessage("添加失败")
    }

    override fun showPrimaryTypeList(typeList: MutableList<TypeEntity>) {

    }

    override fun showTypeListByPrimaryId(typeList: MutableList<TypeEntity>) {
        if(!this::mTypePopup.isInitialized) {
            mTypePopup = XPopup.Builder(curActivity)
                .atView(tv_pay_way)
                .asCustom(TypePopup(curActivity, typeList, object : TypePopup.OnItemClickListener {
                override fun onItemClick(item: TypeEntity, position: Int) {
                    tv_pay_way.text = item.name
                    mTypeId = item.typeId
                    mTypePopup.dismiss()
                }
            })) as TypePopup
        }
        mTypePopup.show()
    }

}

