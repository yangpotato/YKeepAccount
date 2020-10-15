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
import com.base.commom.utils.LogUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.ScaleInAnimation
import com.chad.library.adapter.base.listener.GridSpanSizeLookup
import com.potato.ykeepaccount.AccountApplication
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.addaccount.adapter.CategoryListAdapter
import com.potato.ykeepaccount.addaccount.presenter.CategoryListPresenter
import com.potato.ykeepaccount.addaccount.presenter.ICategoryListContract
import com.potato.ykeepaccount.room.AccountDatabase
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.CategoryResultEntity
import com.potato.ykeepaccount.view.GridRecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import kotlinx.android.synthetic.main.fragment_category_list.*
import java.util.*
import kotlin.collections.ArrayList

class CategoryListFragment : BaseRvFragment<CategoryListPresenter, CategoryEntity>(), ICategoryListContract.View {
    private lateinit var adapter: CategoryListAdapter
    companion object{
        @JvmStatic
        fun newInstance(id : Int) : BaseFragment<*> = CategoryListFragment().apply {
            arguments = Bundle().apply {
                putInt(JumpUtil.P1, id)
            }
        }
    }

    override fun loadData() {

//        //该动画必须需要item个数为列数的倍数，比如一行显示5个，那个数必须为5*n
//        for(index in 1..20){
//            mList.add(index.toString())
//        }

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


//        initRecyclerView(CategoryListAdapter(), normal, GridLayoutManager(context, 4))

//        mAdapter.setGridSpanSizeLookup { gridLayoutManager, viewType, position ->
//            return@setGridSpanSizeLookup if ((mAdapter.data as MutableList<CategoryEntity>)[position].fatherId == 0L) 4 else 1
//        }
        adapter = CategoryListAdapter()
        normal.layoutManager = GridLayoutManager(context, 4)
//        normal.layoutAnimation = AnimationUtils.loadLayoutAnimation(curActivity, R.anim.grid_layout_animation_from_bottom)
        adapter.isAnimationFirstOnly = false
        adapter.animationEnable = true
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
        normal.adapter = adapter

        showNormal()
        mPresenter.getAllCategoryList()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_category_list
    }

    override fun initFragment(savedInstanceState: Bundle?) {

    }

    override fun createPresenter(): CategoryListPresenter {
        return CategoryListPresenter()
    }

    override fun onLoadMore() {

    }

    override fun onRefresh() {

    }

    override fun onItemClick(view: View?, t: CategoryEntity?, position: Int) {

    }

    override fun showAllCategoryList(categoryList: MutableList<CategoryResultEntity>?) {
        LogUtil.d(categoryList.toString())
        val newCategoryList : MutableList<CategoryEntity> = ArrayList()
        categoryList!!.forEach{
            newCategoryList.add(it.categoryEntity)
            newCategoryList.addAll(it.getList())
        }
//        initRecyclerView(CategoryListAdapter(), normal, GridLayoutManager(context, 4))

        adapter.addData(newCategoryList)

    }

}