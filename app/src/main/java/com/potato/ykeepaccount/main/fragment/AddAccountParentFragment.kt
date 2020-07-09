package com.potato.ykeepaccount.main.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.mvp.IBaseContract
import com.base.commom.utils.LogUtil
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.main.adapter.AddAccountPagerAdapter
import com.potato.ykeepaccount.util.viewPager2Helper
import kotlinx.android.synthetic.main.fragment_add_account_parent.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class AddAccountParentFragment : BaseFragment<IBaseContract.Presenter<*>>() {
    private val mTitles = arrayOf("支出", "收入", "转账", "借贷")
    companion object {
        @JvmStatic
        fun newInstance() = AddAccountParentFragment()
    }

    override fun loadData() {

    }

    override fun getLayoutId(): Int {
       return R.layout.fragment_add_account_parent
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        initIndicator()
//        view_pager.adapter = AddAccountPagerAdapter(curActivity as FragmentActivity, mTitles)
//        view_pager.offscreenPageLimit = 3
    }

    override fun createPresenter(): IBaseContract.Presenter<*>? {
        return null
    }

    private fun initIndicator() {
        val commonNavigator = CommonNavigator(curActivity)
        commonNavigator.adapter = object : CommonNavigatorAdapter(){
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val pagerTextView = SimplePagerTitleView(context)
                pagerTextView.text = mTitles[index]
                pagerTextView.normalColor = Color.GRAY
                pagerTextView.selectedColor = Color.BLACK
                pagerTextView.textSize = 18f
                pagerTextView.setOnClickListener { view_pager.currentItem = index }
                return pagerTextView
            }

            override fun getCount(): Int {
                return mTitles.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = BezierPagerIndicator(context)
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"))
                return indicator
            }
        }
        indicator.navigator = commonNavigator

        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                indicator.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
                LogUtil.i("onPageScrolled: position:$position, positionOffset:$positionOffset, positionOffsetPixels$positionOffsetPixels")
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.onPageSelected(position)
                LogUtil.i("onPageSelected: $position")
            }
        })
    }

}