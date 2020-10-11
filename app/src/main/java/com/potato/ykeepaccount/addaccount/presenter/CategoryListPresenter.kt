package com.potato.ykeepaccount.addaccount.presenter

import com.base.commom.mvp.BasePresenter
import com.potato.ykeepaccount.room.BaseRoomPresenter
import com.potato.ykeepaccount.room.RoomObserver
import com.potato.ykeepaccount.room.entity.CategoryResultEntity
import com.potato.ykeepaccount.room.entity.TypeEntity

class CategoryListPresenter: BaseRoomPresenter<ICategoryListContract.View>(), ICategoryListContract.Presenter{
    override fun getAllCategoryList() {
        addSubscribe(createCategoryDao().queryAllCategory(), object : RoomObserver<MutableList<CategoryResultEntity>>(mView){
            override fun onSuccess(data: MutableList<CategoryResultEntity>) {
                mView?.showAllCategoryList(data)
            }
        })
    }
}