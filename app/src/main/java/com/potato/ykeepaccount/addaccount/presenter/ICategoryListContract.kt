package com.potato.ykeepaccount.addaccount.presenter

import com.base.commom.mvp.IBaseContract
import com.potato.ykeepaccount.room.entity.AccountEntity
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.CategoryResultEntity
import com.potato.ykeepaccount.room.entity.TypeEntity

interface ICategoryListContract {
    interface View : IBaseContract.View{
        fun showAllCategoryList(categoryList : MutableList<CategoryResultEntity>?)
    }

    interface Presenter : IBaseContract.Presenter<View>{
        /**
         * 查询标签分类
         */
        fun getAllCategoryList()

    }
}