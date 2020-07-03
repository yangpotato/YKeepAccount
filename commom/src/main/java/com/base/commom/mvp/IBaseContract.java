package com.base.commom.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lxj.xpopup.core.BasePopupView;

import java.util.List;
import java.util.Map;

/**
 * @author 1one
 * @date 2019/8/24.
 */
public interface IBaseContract {

    interface View {

        ProgressDialog getProgressDialog();

        void initProgressDialog();

        void showLoading();

        void showNormal();

        void showError();

        void showLoginPage();

        void showLoadingDialog(String msg);

        void dismissLoadingDialog();

        void showMessage(String msg);

        BasePopupView getLoadingDialog();

        void setList(List list, int count, boolean isLoadMore);
    }

    interface Presenter<V extends View> {

        void onSaveInstanceState(Bundle outState);

        void onRestoreInstanceState(Bundle outState);

        void attachView(@NonNull V view);

        void detachView();

        /**
         * view initialized, you can init view data
         */
        void onViewInitialized();

        @Nullable
        Context getContext();

    }
}
