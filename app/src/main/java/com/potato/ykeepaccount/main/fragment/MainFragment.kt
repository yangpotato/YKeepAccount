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
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.mvp.IBaseContract
import com.base.commom.utils.JumpUtil
import com.base.commom.utils.LogUtil
import com.base.commom.utils.StatusBarUtil
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.addaccount.AddAccountActivity
import com.potato.ykeepaccount.main.presenter.IMainContract
import com.potato.ykeepaccount.main.presenter.MainPresenter
import com.potato.ykeepaccount.room.entity.AccountResultEntity
import kotlinx.android.synthetic.main.fragment_main.*
import java.io.File
import kotlin.math.hypot

class MainFragment : BaseFragment<MainPresenter>(), IMainContract.View {

    companion object {
        @JvmStatic
        fun newInstance() : BaseFragment<*> = MainFragment()
    }

    override fun loadData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initFragment(savedInstanceState: Bundle?) {
        initToolbar()
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
            JumpUtil.overlay(curActivity, AddAccountActivity::class.java)
        }

        iv_ca.setOnClickListener {

            val dbPath = curActivity.getDatabasePath("keep_account.db")
            copy(dbPath, Environment.getExternalStorageDirectory().absolutePath + "/keep_account.db")
        }
        iv_test.setOnClickListener {
            mPresenter.getAccountListByDateRange(0 , 0)
        }

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
    }

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun showAccountList(accountList: List<AccountResultEntity>?) {
        LogUtil.i("accountList: $accountList")
    }


}