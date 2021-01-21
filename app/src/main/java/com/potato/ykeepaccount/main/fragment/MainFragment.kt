package com.potato.ykeepaccount.main.fragment

import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.utils.CalendarUtil
import com.base.commom.utils.DensityUtils
import com.base.commom.utils.LogUtil
import com.base.commom.utils.StatusBarUtil
import com.lxj.xpopup.XPopup
import com.potato.ykeepaccount.AccountApplication
import com.potato.ykeepaccount.Constant
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.addaccount.fragment.CategoryListFragment
import com.potato.ykeepaccount.addaccount.popup.AddAccountPopup
import com.potato.ykeepaccount.main.adapter.HomeAdapter
import com.potato.ykeepaccount.main.presenter.IMainContract
import com.potato.ykeepaccount.main.presenter.MainPresenter
import com.potato.ykeepaccount.room.AccountDatabase
import com.potato.ykeepaccount.room.entity.AccountResultEntity
import com.potato.ykeepaccount.util.HomeItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel

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
            AccountDatabase.getInstance(AccountApplication.instance).close()

            val dbPath = curActivity.getDatabasePath("keep_account.db")
            copy(dbPath, Environment.getExternalStorageDirectory().absolutePath + "/keep_account.db")
//            val dbPath1 = curActivity.getDatabasePath("keep_account.db-shm")
//            copy(dbPath1, Environment.getExternalStorageDirectory().absolutePath + "/keep_account.db-shm")
//            val dbPath2 = curActivity.getDatabasePath("keep_account.db-wal")
//            copy(dbPath2, Environment.getExternalStorageDirectory().absolutePath + "/keep_account.db-wal")

//            val dbFile: File =curActivity.getDatabasePath("keep_account.db")
//            val exportDir = File(
//                Environment.getExternalStorageDirectory(),
//                "dlionBackup"
//            )
//            if (!exportDir.exists()) {
//                exportDir.mkdirs();
//            }
//            val backup = File(exportDir, dbFile.name)
//            val inChannel: FileChannel = FileInputStream(dbFile).getChannel()
//            val outChannel: FileChannel = FileOutputStream(backup).getChannel()
//            try {
//                inChannel.transferTo(0, inChannel.size(), outChannel)
//            } catch (e: IOException) {
//                // TODO Auto-generated catch block
//                e.printStackTrace()
//            } finally {
//                if (inChannel != null) {
//                    inChannel.close()
//                }
//                if (outChannel != null) {
//                    outChannel.close()
//                }
//            }
        }

    }

    private fun initRecyclerView() {
        mAdapter = HomeAdapter(mAccountList)
        recyclerview.layoutManager = LinearLayoutManager(curActivity)
        recyclerview.addItemDecoration(HomeItemDecoration(DensityUtils.dip2px(10f)))
        recyclerview.adapter = mAdapter
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
        mAdapter.setNewInstance(accountList)
    }

}