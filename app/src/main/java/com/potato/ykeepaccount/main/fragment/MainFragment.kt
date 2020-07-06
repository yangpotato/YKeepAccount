package com.potato.ykeepaccount.main.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.RequiresApi
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.mvp.IBaseContract
import com.base.commom.utils.LogUtil
import com.base.commom.utils.StatusBarUtil
import com.potato.ykeepaccount.R
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.math.hypot

class MainFragment : BaseFragment<IBaseContract.Presenter<*>>() {

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
            val mLocation = IntArray(2)
            view.getLocationInWindow(mLocation)
            val centerX = mLocation[0] + view.measuredWidth / 2
            val centerY = mLocation[1] + view.measuredHeight / 2
            view_puppet.post {
                val height = view_puppet.measuredHeight.toDouble()
                val width = view_puppet.measuredWidth.toDouble()
                val maxRadius = hypot(height, width).toFloat()
                LogUtil.i("height: $height, width: $width")
                val animator : Animator = ViewAnimationUtils.createCircularReveal(view_puppet, centerX, centerY, 0f, maxRadius)
                animator.duration = 500
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        view_puppet.visibility = View.VISIBLE
                    }
                })
                animator.start()
            }

        }
    }

    private fun initToolbar() {
        StatusBarUtil.setPaddingSmart(curActivity, fl_toolbar)
    }

    override fun createPresenter(): IBaseContract.Presenter<*>? {
        return null
    }


}