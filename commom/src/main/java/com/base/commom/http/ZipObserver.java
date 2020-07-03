package com.base.commom.http;

import android.text.TextUtils;

import com.base.commom.mvp.IBaseContract;
import com.base.commom.utils.LogUtil;
import com.lxj.xpopup.XPopup;

import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;

public abstract class ZipObserver extends DisposableObserver<HashMap<String, BaseResponse>> {
    private IBaseContract.View mView;
    //是否需要显示加载框
    private boolean isShowProgress;
    //加载框上面的文字
    private String mProgressMsg;

    public ZipObserver(IBaseContract.View mView) {
        this(mView, false);
    }

    public ZipObserver(IBaseContract.View mView, boolean isShowProgress) {
        this(mView, isShowProgress, "");
    }

    public ZipObserver(IBaseContract.View mView, boolean isShowProgress, String progressMsg) {
        this.mView = mView;
        this.isShowProgress = isShowProgress;
        this.mProgressMsg = progressMsg;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isShowProgress && mView != null)
            mView.showLoadingDialog(mProgressMsg);
    }

    @Override
    public void onNext(HashMap<String, BaseResponse> map) {
        if(isShowProgress && mView != null){
            if(mView.getLoadingDialog() != null){
                LogUtil.i("onNext: onDismiss");
                mView.getLoadingDialog().delayDismissWith(XPopup.getAnimationDuration(), new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.i("onNext: onDismiss: onSuccess");
                        buildResult(map);
                    }
                });
            }else {
                mView.dismissLoadingDialog();
            }
        }else
            buildResult(map);
    }

    private void buildResult(HashMap<String, BaseResponse> map) {
        // 遍历map中的值
        String errorMsg = null;
        for (BaseResponse baseResponse : map.values()) {
            if (TextUtils.isEmpty(baseResponse.getError())) {
                continue;
            } else if (baseResponse.getStatue() == 0
                    || baseResponse.getError().contains("重新登录")
                    || baseResponse.getError().contains("另一地点登录")
                    || baseResponse.getError().contains("请先登录")
                    || baseResponse.getError().contains("已超时")) {
                if (isCanJumpLogin()) {
                    mView.showMessage(baseResponse.getError());
                    mView.showLoginPage();
                }
                return;
            } else {
                if (mView.getLoadingDialog() != null && mView.getLoadingDialog().isShow())
                    mView.getLoadingDialog().dismiss();
                mView.showMessage(baseResponse.getError());
                onResultError(baseResponse.getError());
            }
        }
        if (!TextUtils.isEmpty(errorMsg)) {
            mView.showMessage(errorMsg);
        }
        onSuccess(map);
        return;
    }


    protected boolean isCanJumpLogin() {
        return true;
    }


    protected void onResultError(String error){};

    /**
     * 回调正常数据
     *
     * @param data
     */
    protected abstract void onSuccess(HashMap<String, BaseResponse> data);

    @Override
    public void onError(Throwable e) {
        LogUtil.e("HttpUtil", "请求失败：" + e.getMessage());
        if (mView != null) {
            mView.showError();
            mView.showMessage(ExceptionHandler.handleException(e));
        }
    }

    @Override
    public void onComplete() {

    }
}
