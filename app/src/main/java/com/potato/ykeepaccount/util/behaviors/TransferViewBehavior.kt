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
import com.base.commom.utils.LogUtil
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs
import kotlin.math.min


class TransferViewBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {
    companion object{
        //开始缩放动画的节点
        const val ANIM_CHANGE_POINT = 0.1f
    }
    private var mOriginalX = 0f
    private var mOriginalY = 0f

    private var mOriginalSize = 0
    //可滑动的总距离
    private var mTotalScrollRange = 0
    //移动百分比
    private var mPercent = 0f
    //动画插值器
    private var mInterpolatorY : DecelerateInterpolator = DecelerateInterpolator()
    private var mInterpolatorX : AccelerateInterpolator = AccelerateInterpolator()

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
        child.y = mOriginalY - (mOriginalY * mPercentY)

        if(mPercent > ANIM_CHANGE_POINT){
            val mScalePercent = (mPercent - ANIM_CHANGE_POINT) / (1 - ANIM_CHANGE_POINT)
            val mPercentX = mInterpolatorX.getInterpolation(mScalePercent)
            child.x = mOriginalX + (mOriginalX * mPercentX)
//            child.scaleX = 
//            LogUtil.i("mPercentY: $mPercentY, mPercentX: $mPercentX, mPercent: $mPercent, mTotalScrollRange: $mTotalScrollRange")
        }

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
            mTotalScrollRange = (dependency.height * 0.8).toInt()


    }
}