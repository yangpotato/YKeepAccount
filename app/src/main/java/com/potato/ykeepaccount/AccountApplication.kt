package com.potato.ykeepaccount

import com.base.commom.AppApplication
import com.potato.ykeepaccount.room.AccountDatabase

class AccountApplication : AppApplication() {
    companion object{
        lateinit var instance : AccountApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        AccountDatabase.getInstance(this)
    }
}