package com.potato.ykeepaccount.util.behaviors

import android.R.attr
import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.base.commom.AppApplication.getInstance
import com.base.commom.utils.DensityUtils
import com.base.commom.utils.LogUtil
import com.base.commom.utils.StatusBarUtil
import com.google.android.material.appbar.AppBarLayout
import com.potato.ykeepaccount.AccountApplication
import kotlin.math.abs
import kotlin.math.min


class TransferViewBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {
    //起始坐标点
    private var mOriginalX = 0f
    private var mOriginalY = 0f

    private var mOriginalSize = 0
    //可滑动的总距离
    private var mTotalScrollRange = 0
    //移动百分比
    private var mPercent = 0f
    //动画插值器
    private var mInterpolatorX : DecelerateInterpolator = DecelerateInterpolator()
    private var mInterpolatorY : AccelerateInterpolator = AccelerateInterpolator()
    //缩放比例
    private var mScale = 0.66f
    //状态栏高度
    private var mStatusHeight = 0
    //10dp 的像素大小
    private var mDp10 = 0
    //toolbar的高度
    private var mToolBarHeight = 0

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is ImageView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if(dependency !is ImageView)
            return false
        //初始化数据
        initValues(child, dependency)
        mPercent = min(abs(dependency.y) / mTotalScrollRange, 1f)
        val mPercentY = mInterpolatorY.getInterpolation(mPercent)
        child.y = mOriginalY - ((mOriginalY + mOriginalSize * mScale / 2 - mStatusHeight - (mToolBarHeight - mOriginalSize * (1 - mScale)) / 2) * mPercentY)

        val mPercentX = mInterpolatorX.getInterpolation(mPercent)
        child.x = mOriginalX + ((mOriginalX + mOriginalSize * mScale / 2 - mDp10) * mPercentX)
        child.scaleX = 1 - mPercentX * mScale
        child.scaleY = 1 - mPercentX * mScale
//        LogUtil.i("mPercentY: $mPercentY, mPercentX: $mPercentX, mPercent: $mPercent, mTotalScrollRange: $mTotalScrollRange")
        return true
    }

    private fun initValues(child: View, dependency: ImageView) {
        //计算初始X坐标
        if(mOriginalX == 0f)
            mOriginalX = child.x
        //计算初始Y坐标
        if(mOriginalY == 0f)
            mOriginalY = child.y
        //计算初始Y坐标
        if(mOriginalSize == 0)
            mOriginalSize = child.width
        //获取可滑动的总距离
        if(mTotalScrollRange == 0)
            mTotalScrollRange = (dependency.height * 0.8).toInt() - StatusBarUtil.getStatusBarHeight(child.context) - DensityUtils.dip2px(50f)
        if(mDp10 == 0) {
            mDp10 = DensityUtils.dip2px(10f)
            mToolBarHeight = mDp10 * 5
        }
        if(mStatusHeight == 0)
            mStatusHeight = StatusBarUtil.getStatusBarHeight(child.context)
    }
}