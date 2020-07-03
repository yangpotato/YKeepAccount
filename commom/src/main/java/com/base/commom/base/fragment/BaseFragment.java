package com.base.commom.base.fragment;

import android.app.Application;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.base.commom.AppApplication;
import com.base.commom.BaseConstants;
import com.base.commom.mvp.IBaseContract;
import com.hjq.toast.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import java.util.List;
import java.util.Map;

/**
 * @author 1one
 * @date 2019/8/28.
 */
public abstract class BaseFragment<P extends IBaseContract.Presenter>
        extends BaseRootFragment implements IBaseContract.View {
    protected P mPresenter;
    private ProgressDialog mProgressDialog;
    protected BasePopupView mLoadingPopupView;
    /**
     * fragment for viewpager
     */
    private boolean isPagerFragment = false;
    private boolean isFragmentShowed = false;

    protected abstract int getLayoutId();

    /**
     * 创建Presenter实例
     */
    protected abstract P createPresenter();

    @Override
    protected void onFragmentViewCreate() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
            mPresenter.onViewInitialized();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onRestoreInstanceState(getArguments());
        }
        if (mPresenter != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPresenter != null) {
            mPresenter.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        if(getContext() != null)
//            Glide.get(getContext()).onLowMemory();
//    }


    public void onFragmentShowed() {
        isFragmentShowed = true;
    }

    public void onFragmentHided() {
        isFragmentShowed = false;
    }

    protected Application getAppApplication() {
        return AppApplication.getInstance();
    }


    @Override
    public void initProgressDialog() {
        mProgressDialog =  new ProgressDialog(getContext());
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }


    @Override
    public void showMessage(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void showLoadingDialog(String msg) {
        if(mLoadingPopupView == null)
            mLoadingPopupView = new XPopup.Builder(curActivity)
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


    public boolean isPagerFragment() {
        return isPagerFragment;
    }

    public BaseFragment setPagerFragment(boolean flag) {
        isPagerFragment = flag;
        return this;
    }

    public boolean isFragmentShowed() {
        return isFragmentShowed;
    }

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
        showLoading();
    }

    @Override
    public void showLoginPage() {
        jumpToLogin();
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
