package com.base.commom.base.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.base.commom.BaseConstants;
import com.base.commom.listener.PermissionListener;
import com.base.commom.mvp.IBaseContract;
import com.base.commom.utils.JumpUtil;
import com.base.commom.utils.ToastUtil;
import com.base.commom.widget.BaseCenterPopup;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;
import java.util.Map;

/**
 * @author 1one
 * @date 2019/8/28.
 */
public abstract class BaseActivity<P extends IBaseContract.Presenter> extends BaseRootActivity implements IBaseContract.View {

    protected P mPresenter;
    protected BasePopupView mLoadingPopupView;

    @Override
    protected void onViewCreated() {
        mPresenter = createPresenter();
        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    /**
     * 创建Presenter实例
     */
    protected abstract P createPresenter();


    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void showNormal() {
        showNormalView();
    }

    @Override
    public void showError() {
        showErrorView();
    }

    @Override
    protected void reload() {

    }

    @Override
    public void showLoginPage() {
        jumpToLogin();
    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    public ProgressDialog getProgressDialog() {
        return null;
    }

    @Override
    public void showMessage(String msg) {
        ToastUtil.show(curActivity, msg);
    }

    @Override
    public void showLoadingDialog(String msg) {
        if(mLoadingPopupView == null)
            mLoadingPopupView = new XPopup.Builder(this)
                    .asLoading(TextUtils.isEmpty(msg)?"加载中":msg);
        if(mLoadingPopupView instanceof LoadingPopupView)
            ((LoadingPopupView) mLoadingPopupView).setTitle(TextUtils.isEmpty(msg)?"加载中":msg);
        if(mLoadingPopupView.isShow())
            return;
        mLoadingPopupView.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if(mLoadingPopupView != null && mLoadingPopupView.isShow()) {
            mLoadingPopupView.dismiss();
        }
    }

    @Override
    public BasePopupView getLoadingDialog() {
        return mLoadingPopupView;
    }


    protected void getPermission(String permission, PermissionListener permissionListener) {
        AndPermission.with(this)
                .runtime()
                .permission(permission)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    permissionListener.accept(permission);
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    permissionListener.refuse(permission);
                })
                .start();
    }

    protected int getScreenWidth() {
        WindowManager wm = (WindowManager) curActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    protected int getScreenHeight() {
        WindowManager wm = (WindowManager) curActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
    }

    public boolean checkLogin(){
        if(TextUtils.isEmpty(BaseConstants.token))
            return false;
        return true;
    }

    @Override
    public void setList(List list, int count, boolean isLoadMore) {

    }

    protected BasePopupView mCustPopupView;

}
