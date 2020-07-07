package com.potato.ykeepaccount.addaccount

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.base.commom.base.activity.BaseActivity
import com.base.commom.mvp.IBaseContract
import com.base.commom.utils.DensityUtils
import com.potato.ykeepaccount.view.ScaleTransitionPagerTitleView
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.main.adapter.AddAccountPagerAdapter
import com.potato.ykeepaccount.util.viewPager2Helper
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_add_account.*
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator

class AddAccountActivity : BaseActivity<IBaseContract.Presenter<*>>() {
    private val mTitles = arrayOf("支出", "收入", "转账", "借贷")

    override fun createPresenter(): IBaseContract.Presenter<*>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_account
    }

    override fun initToolbar() {
        initIndicator()
        view_pager.adapter = AddAccountPagerAdapter(curActivity as FragmentActivity, mTitles)
        view_pager.offscreenPageLimit = 3
    }

    override fun initActivity() {
        initTagList()
    }

    private fun initTagList() {

    }

    private fun initIndicator() {
        val commonNavigator = CommonNavigator(curActivity)
        commonNavigator.adapter = object : CommonNavigatorAdapter(){
            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val pagerTextView = ScaleTransitionPagerTitleView(context)
                pagerTextView.text = mTitles[index]
                pagerTextView.normalColor = Color.parseColor("#72FFFFFF")
                pagerTextView.selectedColor = Color.WHITE
                pagerTextView.textSize = 18f
                pagerTextView.setOnClickListener { view_pager.currentItem = index }
                return pagerTextView
            }

            override fun getCount(): Int {
                return mTitles.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = BezierPagerIndicator(context)
                indicator.setColors(Color.WHITE)
                indicator.maxCircleRadius = DensityUtils.dip2px(3f).toFloat()
                indicator.minCircleRadius = DensityUtils.dip2px(2f).toFloat()
                return indicator
            }
        }
        indicator.navigator = commonNavigator
        viewPager2Helper(indicator, view_pager)
    }

}