package com.potato.ykeepaccount.main.presenter

import com.base.commom.mvp.IBaseContract
import com.potato.ykeepaccount.room.entity.AccountEntity
import com.potato.ykeepaccount.room.entity.AccountResultEntity
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.TypeEntity

interface IMainContract {
    interface View : IBaseContract.View{
        fun showAccountList(accountList : MutableList<AccountResultEntity>?)

    }

    interface Presenter : IBaseContract.Presenter<View>{
        /**
         * 查询最近使用的标签分类
         */
        fun getAccountListByDateRange(startDate : Long, endDateLong: Long)

    }
}