package com.potato.ykeepaccount.addaccount.presenter

import com.base.commom.mvp.BasePresenter
import com.potato.ykeepaccount.AccountApplication
import com.potato.ykeepaccount.room.AccountDatabase
import com.potato.ykeepaccount.room.BaseRoomPresenter
import com.potato.ykeepaccount.room.RoomObserver
import com.potato.ykeepaccount.room.entity.AccountEntity
import com.potato.ykeepaccount.room.entity.CategoryEntity
import com.potato.ykeepaccount.room.entity.TypeEntity

class AddAccountPresenter : BaseRoomPresenter<IAddAccountContract.View>(), IAddAccountContract.Presenter{
    override fun getDefaultCategoryList() {
        addSubscribe(createCategoryDao().queryDefaultCategory(), object : RoomObserver<List<CategoryEntity>>(mView){
            override fun onSuccess(data: List<CategoryEntity>) {
                mView?.showDefaultCategoryList(data)
            }
        })
    }

    override fun getPrimaryTypeList() {
        addSubscribe(createTypeDao().queryPrimaryTypeList(), object : RoomObserver<List<TypeEntity>>(mView){
            override fun onSuccess(data: List<TypeEntity>) {
                mView?.showPrimaryTypeList(data)
            }
        })
    }

    override fun getTypeListByPrimaryId(id : Long) {
        addSubscribe(createTypeDao().queryTypeListByPrimaryId(id), object : RoomObserver<List<TypeEntity>>(mView){
            override fun onSuccess(data: List<TypeEntity>) {
                mView?.showTypeListByPrimaryId(data)
            }
        })
    }


    override fun addAccount(accountEntity: AccountEntity) {

        addSubscribe(createAccountDao().addAccount(accountEntity), object : RoomObserver<Long>(mView){
            override fun onSuccess(data: Long) {
                mView?.addAccountSuccess(data)
            }

        })
    }
}