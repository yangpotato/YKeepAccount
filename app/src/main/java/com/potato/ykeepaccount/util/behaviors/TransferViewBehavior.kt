package com.potato.ykeepaccount.util.behaviors

import android.R.attr
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.base.commom.utils.LogUtil
import kotlin.math.abs


class TransferViewBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {
    var mOriginalX = 0
    var mOriginalY = 0

    /**
     * 处于中心时候原始X轴
     */
    private var mOriginalHeaderX = 0

    /**
     * 处于中心时候原始Y轴
     */
    private var mOriginalHeaderY = 0

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return true
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        var dependencyWidth = dependency.width
        var childWidth = child.width
        LogUtil.i("dependencyWidth: $dependencyWidth ， childWidth： $childWidth")
        //计算初始X坐标
        if(mOriginalX == 0)
            mOriginalX = dependency.width / 2 - child.width / 2
        //计算初始Y坐标
        if(mOriginalY == 0)
            mOriginalY = dependency.height - child.height / 2
        //X轴百分比
        var mPercentX = abs(dependency.y / mOriginalX)
        if(mPercentX > 1)
            mPercentX = 1f
        //Y轴百分比
        var mPercentY = abs(dependency.y / mOriginalY)
        if(mPercentY > 1)
            mPercentY = 1f
//        LogUtil.i("mPercentX: $mPercentX, mPercentY: $mPercentY")
        var x: Float = mOriginalX - mOriginalX * mPercentX
        if (x <= child.width) {
            x = child.width.toFloat()
        }

//        child.x = max(mOriginalX - mOriginalX * mPercentX, child.width.toFloat())
        child.x = abs(x)
        child.y = abs(mOriginalY - mOriginalY * mPercentY)
        return true

    }
}