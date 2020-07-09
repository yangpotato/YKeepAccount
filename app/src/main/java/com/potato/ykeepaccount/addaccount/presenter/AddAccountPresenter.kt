package com.potato.ykeepaccount.addaccount.presenter

import com.base.commom.mvp.BasePresenter
import com.potato.ykeepaccount.AccountApplication
import com.potato.ykeepaccount.room.AccountDatabase
import com.potato.ykeepaccount.room.RoomObserver
import com.potato.ykeepaccount.room.entity.AccountEntity
import com.potato.ykeepaccount.room.entity.CategoryEntity

class AddAccountPresenter : BasePresenter<IAddAccountContract.View>(), IAddAccountContract.Presenter{
    override fun getDefaultCategoryList() {
        addSubscribe(AccountDatabase.getInstance(AccountApplication.instance).categoryDao().queryDefaultCategory(), object : RoomObserver<List<CategoryEntity>>(mView){
            override fun onSuccess(data: List<CategoryEntity>) {
                mView?.showDefaultCategoryList(data)
            }
        })
    }

    override fun addAccount(accountEntity: AccountEntity) {

        addSubscribe(AccountDatabase.getInstance(AccountApplication.instance).accountDao().addAccount(accountEntity), object : RoomObserver<Long>(mView){
            override fun onSuccess(data: Long) {
                mView?.addAccountSuccess(data)
            }

        })
    }
}