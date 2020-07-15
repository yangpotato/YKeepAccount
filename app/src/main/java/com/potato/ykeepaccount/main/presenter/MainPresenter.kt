package com.potato.ykeepaccount.main.presenter

import com.potato.ykeepaccount.room.BaseRoomPresenter
import com.potato.ykeepaccount.room.RoomObserver
import com.potato.ykeepaccount.room.entity.AccountResultEntity

class MainPresenter : BaseRoomPresenter<IMainContract.View>(), IMainContract.Presenter {
    override fun getAccountListByDateRange(startDate: Long, endDateLong: Long) {
        addSubscribe(createAccountDao().getAccount(), object : RoomObserver<MutableList<AccountResultEntity>>(mView){
            override fun onSuccess(t: MutableList<AccountResultEntity>) {
                mView.showAccountList(t)
            }
        })
    }
}