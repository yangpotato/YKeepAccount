package com.potato.ykeepaccount.addaccount.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.base.fragment.BaseRvFragment
import com.base.commom.mvp.IBaseContract
import com.base.commom.utils.JumpUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.ScaleInAnimation
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.addaccount.adapter.CategoryListAdapter
import com.potato.ykeepaccount.view.GridRecyclerView
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import kotlinx.android.synthetic.main.fragment_category_list.*
import java.util.*
import kotlin.collections.ArrayList

class CategoryListFragment : BaseRvFragment<IBaseContract.Presenter<*>, String>() {

    companion object{
        @JvmStatic
        fun newInstance(id : Int) : BaseFragment<*> = CategoryListFragment().apply {
            arguments = Bundle().apply {
                putInt(JumpUtil.P1, id)
            }
        }
    }

    override fun loadData() {
        mList = Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
        val adapter = CategoryListAdapter(mList)
//        normal.setHasFixedSize(true)
//        adapter.animationEnable = true
//        adapter.isAnimationFirstOnly = true
//        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInRight)
//        adapter.adapterAnimation = SlideInRightAnimator()
//        initRecyclerView(adapter, normal, GridLayoutManager(curActivity, 4))
//        normal.itemAnimator = SlideInLeftAnimator()
//        initRecyclerView(adapter, normal as GridRecyclerView)
//        initRecyclerView(adapter, normal)
//        mList = Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
//        setList(ArrayList(Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")), 0, true)

        normal.layoutManager = GridLayoutManager(curActivity, 4)
        normal.layoutAnimation = AnimationUtils.loadLayoutAnimation(curActivity, R.anim.grid_layout_animation_from_bottom)
        normal.adapter = adapter

//        mList = Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
//        setList(ArrayList(Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")), 0, false)

        showNormal()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_category_list
    }

    override fun initFragment(savedInstanceState: Bundle?) {

    }

    override fun createPresenter(): IBaseContract.Presenter<*>? {
        return null
    }

    override fun onLoadMore() {

    }

    override fun onRefresh() {

    }

    override fun onItemClick(view: View?, t: String?, position: Int) {

    }

}