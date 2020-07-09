package com.potato.ykeepaccount

import com.base.commom.AppApplication

class AccountApplication : AppApplication() {
    companion object{
        lateinit var instance : AccountApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}