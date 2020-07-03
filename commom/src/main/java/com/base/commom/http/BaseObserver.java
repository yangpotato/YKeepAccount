package com.base.commom.http;

import android.text.TextUtils;
import android.widget.TextView;

import com.base.commom.mvp.IBaseContract;
import com.base.commom.utils.LogUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupStatus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseObserver<T> extends DisposableObserver<BaseResponse<T>> {
    private IBaseContract.View mView;
    //是否需要显示加载框
    private boolean isShowProgress;

    /**
     * 当需要多次显示加载框，传入true
     * 如果在回调中另外进行了逻辑判断，判断失败时，需要手动调用dismissLoadingDialog
     */
    private boolean isRepeatShowProgress;
    //加载框上面的文字
    private String mProgressMsg;

    public BaseObserver(IBaseContract.View mView) {
        this(mView, false);
    }

    public BaseObserver(IBaseContract.View mView, boolean isShowProgress) {
        this(mView, isShowProgress, false, "");
    }

    public BaseObserver(IBaseContract.View mView, boolean isShowProgress, boolean isRepeatShowProgress) {
        this(mView, isShowProgress, isRepeatShowProgress, "");
    }

    public BaseObserver(IBaseContract.View mView, boolean isShowProgress, String progressMsg) {
        this(mView, isShowProgress, false, progressMsg);
    }

    public BaseObserver(IBaseContract.View mView, boolean isShowProgress, boolean isRepeatShowProgress, String progressMsg) {
        this.mView = mView;
        this.isShowProgress = isShowProgress;
        this.mProgressMsg = progressMsg;
        this.isRepeatShowProgress = isRepeatShowProgress;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isShowProgress && mView != null)
            mView.showLoadingDialog(mProgressMsg);
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        if (isShowProgress && mView != null) {
            if (isRepeatShowProgress) {
                LogUtil.i("onNext: isRepeatShowProgress");
                buildResult(baseResponse);
            } else if (mView.getLoadingDialog() != null) {
                LogUtil.i("onNext: onDismiss");
                mView.getLoadingDialog().delayDismissWith(XPopup.getAnimationDuration(), new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.i("onNext: onDismiss: onSuccess");
                        buildResult(baseResponse);
                    }
                });
            } else {
                mView.dismissLoadingDialog();
            }
        } else
            buildResult(baseResponse);
    }

    protected boolean isCanJumpLogin() {
        return true;
    }

    private void buildResult(BaseResponse<T> baseResponse) {
        LogUtil.d("============================> errorMsg = " + baseResponse.getError());
        if (TextUtils.isEmpty(baseResponse.getError()))
            onSuccess(baseResponse.getData());
        else if (baseResponse.getStatue() == 0
                || baseResponse.getError().contains("重新登录")
                || baseResponse.getError().contains("另一地点登录")
                || baseResponse.getError().contains("请先登录")
                || baseResponse.getError().contains("已超时")) {
            if (mView.getLoadingDialog() != null && mView.getLoadingDialog().isShow())
                mView.getLoadingDialog().dismiss();
            onResultError(baseResponse.getError());
            if (isCanJumpLogin()) {
                mView.showMessage(baseResponse.getError());
                mView.showLoginPage();
            }
        }else {
            if (mView.getLoadingDialog() != null && mView.getLoadingDialog().isShow())
                mView.getLoadingDialog().dismiss();
            mView.showMessage(baseResponse.getError());
            mView.showNormal();
            onResultError(baseResponse.getError());
        }
        //ApiException(ExceptionHandler.NOT_LOGIN, baseResponse.getError());
    }

    protected void onResultError(String error) {

    }

    /**
     * 回调正常数据
     *
     * @param data
     */
    protected abstract void onSuccess(T data);

    @Override
    public void onError(Throwable e) {
        LogUtil.e("OkGo", "请求失败：" + e.getMessage());
        if (mView != null) {
            mView.dismissLoadingDialog();
            mView.showMessage(ExceptionHandler.handleException(e));
            mView.showNormal();
        }

//        if(e instanceof ApiException){
//            ApiException exception = (ApiException) e;
//            if(exception.getErrcode() == ExceptionHandler.NOT_LOGIN)
//                mView.showLoginPage();
//        }
    }

    @Override
    public void onComplete() {

    }


}
