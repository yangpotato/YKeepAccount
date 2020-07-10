package com.base.commom.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.base.commom.entity.SuccessEntity;
import com.base.commom.http.BaseHttpApi;
import com.base.commom.http.BaseObserver;
import com.base.commom.http.BaseResponse;
import com.base.commom.http.RetrofitClient;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.functions.Function1;

/**
 * @author 1one
 * @date 2019/8/27.
 */
public abstract class BasePresenter<V extends IBaseContract.View> implements IBaseContract.Presenter<V> {

    //View
    protected V mView;
    private boolean isAttached = false;
    private boolean isViewInitialized = false;
    protected int page = 0;

    public CompositeDisposable compositeDisposable;

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onRestoreInstanceState(Bundle outState) {
        if (outState == null) {
            return;
        }
    }

    /**
     * 绑定View
     * @param view view
     */
    @Override
    public void attachView(@NonNull V view) {
        mView = view;
        onViewAttached();
        isAttached = true;
    }

    /**
     * 取消View绑定
     */
    @Override
    public void detachView() {
        mView = null;
        clearRequest();
    }

    @Override
    public void onViewInitialized() {
        isViewInitialized = true;
    }

    protected boolean isViewInitialized() {
        return isViewInitialized;
    }

    /**
     * presenter和view绑定成功
     */
    @CallSuper
    protected void onViewAttached() {

    }

    /**
     * 获取上下文，需在onViewAttached()后调用
     * @return
     */
    @NonNull
    @Override
    public Context getContext() {
        if (mView instanceof Activity) {
            return (Context) mView;
        } else if (mView instanceof Fragment) {
            return ((Fragment) mView).getContext();
        } else {
            throw new NullPointerException("BasePresenter:mView is't instance of Context,can't use getContext() method.");
        }
    }

    @NonNull
    protected String getString(@StringRes int resId) {
        return getContext().getResources().getString(resId);
    }

    private void clearRequest(){
        if(compositeDisposable != null){
            compositeDisposable.dispose();
        }
    }

    protected void addSubscribe(Disposable disposable){
        if(compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    protected void addSubscribe(Observable<?> observable, DisposableObserver observer){
        addSubscribe(observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

//
    protected void addSubscribe(Single<?> observable, DisposableSingleObserver observer){
        addSubscribe(observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    /**
     * 创建请求
     * @param clazz API
     * @param <T>
     * @return
     */
    protected <T> T create(Class<T> clazz) {
        return RetrofitClient.getInstance().getRetrofit().create(clazz);
    }

}
