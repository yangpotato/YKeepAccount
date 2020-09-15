package com.potato.ykeepaccount.addaccount.popup

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.base.commom.utils.DensityUtils
import com.lxj.xpopup.core.BottomPopupView
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.main.adapter.AddAccountPagerAdapter
import com.potato.ykeepaccount.util.viewPager2Helper
import com.potato.ykeepaccount.view.ScaleTransitionPagerTitleView
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_account.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator
import java.util.*
import java.util.concurrent.TimeUnit

class AddAccountPopup(context: Context) : BottomPopupView(context) {
    private val mTitles = arrayOf("支出", "收入", "转账", "借贷")
    private lateinit var indicator : MagicIndicator
    private lateinit var viewPager : ViewPager2

    override fun getImplLayoutId(): Int {
        return R.layout.popup_add_account
    }

    override fun onCreate() {
        super.onCreate()
        indicator = findViewById(R.id.indicator)
        viewPager = findViewById(R.id.view_pager2)
        initIndicator()
        viewPager.adapter =
            AddAccountPagerAdapter(
                context as FragmentActivity,
                mTitles
            )
        viewPager.offscreenPageLimit = 3
    }

    private fun initIndicator() {
        val commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = object : CommonNavigatorAdapter(){
            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val pagerTextView = ScaleTransitionPagerTitleView(context)
                pagerTextView.text = mTitles[index]
                pagerTextView.normalColor = Color.parseColor("#72FFFFFF")
                pagerTextView.selectedColor = Color.WHITE
                pagerTextView.textSize = 18f
                pagerTextView.setOnClickListener { viewPager.currentItem = index }
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
        viewPager2Helper(indicator, viewPager)
    }

    override fun onShow() {
        super.onShow()

    }

}