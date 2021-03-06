package com.potato.ykeepaccount.util.behaviors

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.base.commom.utils.DensityUtils
import com.base.commom.utils.LogUtil
import com.base.commom.utils.StatusBarUtil
import com.potato.ykeepaccount.R
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlin.math.min

class TransparentBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<FrameLayout>(context, attrs) {
    private var mToolbarHeight : Int = 0
    private lateinit var mOrderView : View
    private var mOrderStartX  = 0f

    private var mDp40 = 0

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: FrameLayout,
        dependency: View
    ): Boolean {
        return dependency is TextView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: FrameLayout,
        dependency: View
    ): Boolean {
        if(mToolbarHeight == 0)
            mToolbarHeight = child.bottom * 2
        if(!this::mOrderView.isInitialized) {
            mOrderView = child.findViewById(R.id.ll_order)
            mOrderStartX = mOrderView.x
            mDp40 = DensityUtils.dip2px(40f)
        }
        val percent = min(dependency.y / mToolbarHeight, 1f)
//        LogUtil.i("percent: $percent")
        child.setBackgroundColor(Color.argb((percent * 255).toInt(), 255, 255, 255))
        mOrderView.x =  mOrderStartX - (mDp40 * percent)
        return true
    }
}