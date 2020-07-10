package com.potato.ykeepaccount.room

import com.base.commom.mvp.BasePresenter
import com.base.commom.mvp.IBaseContract
import com.potato.ykeepaccount.AccountApplication
import com.potato.ykeepaccount.room.dao.AccountDao
import com.potato.ykeepaccount.room.dao.CategoryDao
import com.potato.ykeepaccount.room.dao.TypeDao
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class BaseRoomPresenter<V : IBaseContract.View> : BasePresenter<V>() {


    /**
     * 添加订阅
     */
//    protected open fun addSubscribe(
//        observable: Single<*>,
//        observer: DisposableSingleObserver<in Any>
//    ) {
//        addSubscribe(
//            observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith<DisposableSingleObserver<in Any>>(observer)
//        )
//    }

    private fun createRoom() : AccountDatabase{
        return AccountDatabase.getInstance(AccountApplication.instance)
    }

    fun createAccountDao() : AccountDao{
        return  createRoom().accountDao()
    }

    fun createCategoryDao() : CategoryDao{
        return  createRoom().categoryDao()
    }

    fun createTypeDao() : TypeDao{
        return  createRoom().typeDao()
    }

}