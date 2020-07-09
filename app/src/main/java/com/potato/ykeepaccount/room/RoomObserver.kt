package com.potato.ykeepaccount.room

import com.base.commom.mvp.IBaseContract
import com.base.commom.utils.LogUtil
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver

abstract class RoomObserver<T>(private var mView: IBaseContract.View?, var isShowProgress : Boolean= false, var mProgressMsg: String = "")
    : DisposableSingleObserver<T>() {

    override fun onError(e: Throwable) {
        TODO("Not yet implemented")
    }
    //
//    override fun onStart() {
//        super.onStart()
//        if (isShowProgress) mView?.showLoadingDialog(mProgressMsg)
//    }
//
//    override fun onComplete() {
//    }
//
//    override fun onNext(t: T) {
//        onSuccess(t)
//    }
//
//    override fun onError(e: Throwable) {
//        LogUtil.e("报错信息: $e")
//        mView?.showMessage(e.toString())
//    }

    /**
     * 回调正常数据
     *
     * @param data
     */
//    public abstract fun onSuccess(data: T)
}