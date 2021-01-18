package com.potato.ykeepaccount.main.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.mvp.IBaseContract
import com.base.commom.utils.*
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.SimpleCallback
import com.potato.ykeepaccount.Constant
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.addaccount.AddAccountActivity
import com.potato.ykeepaccount.addaccount.fragment.CategoryListFragment
import com.potato.ykeepaccount.addaccount.popup.AddAccountPopup
import com.potato.ykeepaccount.main.adapter.HomeAdapter
import com.potato.ykeepaccount.main.presenter.IMainContract
import com.potato.ykeepaccount.main.presenter.MainPresenter
import com.potato.ykeepaccount.room.entity.AccountResultEntity
import com.potato.ykeepaccount.util.HomeItemDecoration
import com.scwang.smart.refresh.layout.util.DesignUtil
import kotlinx.android.synthetic.main.fragment_main.*
import java.io.File
import kotlin.math.hypot

class MainFragment : BaseFragment<MainPresenter>(), IMainContract.View {
    private lateinit var mAdapter: HomeAdapter
    private var mAccountList : MutableList<AccountResultEntity> = ArrayList()
    companion object {
        @JvmStatic
        fun newInstance() : BaseFragment<*> = MainFragment()
    }

    override fun loadData() {

    }

    override fun loadDataOnResume() {
        if(Constant.isUpdateAccount && mPresenter != null)
            mPresenter.getAccountListByDateRange(CalendarUtil.getFirstDayOfMonthTime(), CalendarUtil.getFinalDayOnMonthTime())
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initFragment(savedInstanceState: Bundle?) {
        initToolbar()
        initRecyclerView()
        //获取本月账单
        mPresenter.getAccountListByDateRange(CalendarUtil.getFirstDayOfMonthTime(), CalendarUtil.getFinalDayOnMonthTime())
        iv_add.setOnClickListener { view ->
//            val mLocation = IntArray(2)
//            view.getLocationInWindow(mLocation)
//            val centerX = mLocation[0] + view.measuredWidth / 2
//            val centerY = mLocation[1] + view.measuredHeight / 2
//            view_puppet.post {
//                val height = view_puppet.measuredHeight.toDouble()
//                val width = view_puppet.measuredWidth.toDouble()
//                val maxRadius = hypot(height, width).toFloat()
//                LogUtil.i("height: $height, width: $width")
//                val animator : Animator = ViewAnimationUtils.createCircularReveal(view_puppet, centerX, centerY, 0f, maxRadius)
//                animator.duration = 500
//                animator.addListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator?) {
//                        super.onAnimationEnd(animation)
//                        view_puppet.visibility = View.VISIBLE
//                    }
//                })
//                animator.start()
//            }
//            JumpUtil.overlay(curActivity, AddAccountActivity::class.java)
            XPopup.Builder(curActivity)
//                .setPopupCallback(object : SimpleCallback(){
//                override fun onBackPressed(): Boolean {
//
//                    return true
//                }
//            }).
                .asCustom(AddAccountPopup(curActivity)).show()
        }

        iv_ca.setOnClickListener {

            val dbPath = curActivity.getDatabasePath("keep_account.db")
            copy(dbPath, Environment.getExternalStorageDirectory().absolutePath + "/keep_account.db")
        }

    }

    private fun initRecyclerView() {
        mAdapter = HomeAdapter(mAccountList)
        recyclerview.layoutManager = LinearLayoutManager(curActivity)
        recyclerview.addItemDecoration(HomeItemDecoration(DensityUtils.dip2px(50f) + StatusBarUtil.getStatusBarHeight(curActivity)))
        recyclerview.adapter = mAdapter
    }


    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)

        if(childFragment is CategoryListFragment)
            showMessage("是1")
        else
            showMessage("否1")
    }

    private fun copy(f1: File, path2: String) {
        val path = f1.absolutePath
        LogUtil.i("dbPath: $path")
//        val f1 = File(dbPath)
        val size = f1.length()
        LogUtil.i("文件大小: $size")
        val f2 = File(path2)

        f1.runCatching {
            takeIf { it.exists() }?.inputStream()?.use { inputStream ->
                f2.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }.onFailure { // print or throw }
            LogUtil.i("失败")
        }
    }

    private fun initToolbar() {
        StatusBarUtil.setPaddingSmart(curActivity, fl_toolbar)
        toolbar_layout.minimumHeight = StatusBarUtil.getStatusBarHeight(curActivity) + DensityUtils.dip2px(50f)
    }

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun showAccountList(accountList: MutableList<AccountResultEntity>?) {
        LogUtil.i("accountList: $accountList")
        mAdapter.setNewInstance(accountList)
    }

}