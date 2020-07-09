package com.potato.ykeepaccount.util

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.subscribeDbResult(onSuccess:(data : T) -> Unit, onFailed:(data : Throwable) -> Unit){
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : DisposableSingleObserver<T>() {
            override fun onSuccess(t: T) {
               onSuccess(t)
            }

            override fun onError(e: Throwable) {
                onFailed(e)
            }

        })
}