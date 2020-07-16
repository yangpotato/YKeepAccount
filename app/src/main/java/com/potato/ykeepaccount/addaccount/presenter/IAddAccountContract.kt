package com.potato.ykeepaccount.addaccount.presenter

import com.base.commom.mvp.IBaseContract
import com.potato.ykeepaccount.room.entity.AccountEntity
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.TypeEntity

interface IAddAccountContract {
    interface View : IBaseContract.View{
        fun showDefaultCategoryList(categoryList : MutableList<CategoryEntity>?)

        fun addAccountSuccess(id : Long)

        fun showPrimaryTypeList(typeList : MutableList<TypeEntity>)

        fun showTypeListByPrimaryId(typeList : MutableList<TypeEntity>)
    }

    interface Presenter : IBaseContract.Presenter<View>{
        /**
         * 查询最近使用的标签分类
         */
        fun getDefaultCategoryList()

        /**
         * 查询类别
         */
        fun getPrimaryTypeList()

        /**
         * 通过一级ID查询类别
         */
        fun getTypeListByPrimaryId(id : Long)

        /**
         * 增加记账
         */
        fun addAccount(accountEntity: AccountEntity)
    }
}