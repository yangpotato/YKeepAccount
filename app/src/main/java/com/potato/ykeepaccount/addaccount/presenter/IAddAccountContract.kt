package com.potato.ykeepaccount.addaccount.presenter

import com.base.commom.mvp.IBaseContract
import com.potato.ykeepaccount.room.entity.AccountEntity
import com.potato.ykeepaccount.room.entity.CategoryEntity

interface IAddAccountContract {
    interface View : IBaseContract.View{
        fun showDefaultCategoryList(categoryList : List<CategoryEntity>?)

        fun addAccountSuccess(id : Long)
    }

    interface Presenter : IBaseContract.Presenter<View>{
        /**
         * 查询最近使用的标签
         */
        fun getDefaultCategoryList()

        /**
         * 增加记账
         */
        fun addAccount(accountEntity: AccountEntity)
    }
}