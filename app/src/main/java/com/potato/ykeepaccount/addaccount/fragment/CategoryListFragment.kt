package com.potato.ykeepaccount.addaccount.fragment

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.Slide
import com.base.commom.base.fragment.BaseFragment
import com.base.commom.base.fragment.BaseRvFragment

import com.base.commom.utils.JumpUtil
import com.base.commom.utils.LogUtil
import com.potato.ykeepaccount.R
import com.potato.ykeepaccount.addaccount.adapter.CategoryListAdapter
import com.potato.ykeepaccount.addaccount.popup.AddAccountPopup
import com.potato.ykeepaccount.addaccount.presenter.CategoryListPresenter
import com.potato.ykeepaccount.addaccount.presenter.ICategoryListContract
import com.potato.ykeepaccount.main.fragment.MainFragment
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.CategoryResultEntity
import kotlinx.android.synthetic.main.fragment_category_list.*

class CategoryListFragment : BaseRvFragment<CategoryListPresenter, CategoryEntity>(), ICategoryListContract.View {
    private lateinit var adapter: CategoryListAdapter
    private lateinit var mAddAccountFragment: AddAccountFragment

    companion object{
        @JvmStatic
        fun newInstance(id : Int) : CategoryListFragment = CategoryListFragment().apply {
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

//


//        adapter = CategoryListAdapter()
//        normal.layoutManager = GridLayoutManager(context, 4)
////        normal.layoutAnimation = AnimationUtils.loadLayoutAnimation(curActivity, R.anim.grid_layout_animation_from_bottom)
//        adapter.isAnimationFirstOnly = false
//        adapter.animationEnable = true
//        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
//        normal.adapter = adapter

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

    public fun onBack(){
        LogUtil.i("onBack")
        childFragmentManager.beginTransaction().remove(mAddAccountFragment).commit()
    }

    override fun onItemClick(view: View?, entity: CategoryEntity?, position: Int) {
        mAddAccountFragment = AddAccountFragment.newInstance(0)
        val slideTransition = Slide(Gravity.RIGHT)
        slideTransition.duration = 300

        val changeBoundsTransition = ChangeBounds()
        changeBoundsTransition.duration = 300

        mAddAccountFragment.enterTransition = slideTransition
        mAddAccountFragment.exitTransition = slideTransition
        mAddAccountFragment.allowEnterTransitionOverlap = true
        mAddAccountFragment.allowReturnTransitionOverlap = true
        mAddAccountFragment.sharedElementEnterTransition = changeBoundsTransition;

        childFragmentManager.beginTransaction().add(R.id.parent, mAddAccountFragment).commit()
    }

    override fun showAllCategoryList(categoryList: MutableList<CategoryResultEntity>?) {
        LogUtil.d(categoryList.toString())
        val newCategoryList : MutableList<CategoryEntity> = ArrayList()
        categoryList!!.forEach{
            if(it.getList().size != 0) {
                newCategoryList.add(it.categoryEntity)
                newCategoryList.addAll(it.getList())
            }
        }
        if(newCategoryList.size % 4 != 0){
            for(i in 1..(newCategoryList.size % 4)){
                newCategoryList.add(CategoryEntity("123123", 0, -1, 0, "", 0))
            }
        }
        LogUtil.d("categoryList.size: ${newCategoryList.size}")
        normal.layoutAnimation = AnimationUtils.loadLayoutAnimation(curActivity, R.anim.grid_layout_animation_from_bottom)
        initRecyclerView(CategoryListAdapter(), normal, GridLayoutManager(context, 4))
        mAdapter.setGridSpanSizeLookup { _, _, position ->
            return@setGridSpanSizeLookup if ((mAdapter.data as MutableList<CategoryEntity>)[position].fatherId == 0L) 4 else 1
        }
        mList = newCategoryList
        mAdapter.setNewInstance(newCategoryList)

    }

}