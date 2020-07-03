package com.base.commom.base.activity;


import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.base.commom.BaseConstants;
import com.base.commom.R;
import com.base.commom.listener.OnCommonRefreshLoadMoreListener;
import com.base.commom.utils.JumpUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

public abstract class BaseRootActivity extends BaseSupportActivity {
    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;

    protected View mNormalView, mLoadingView, mErrorView;
    private int mCurrentState = LOADING_STATE;

    protected Toolbar mToolbar;

    @Override
    protected void initViewStatue() {
        mNormalView = findViewById(R.id.normal);
        mToolbar = findViewById(R.id.toolbar);
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            if(getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishWithAnim();
                }
            });
        }
        if(mNormalView != null) {
            if (!(mNormalView.getParent() instanceof ViewGroup)) {
                throw new IllegalStateException(
                        "mNormalView's ParentView should be a ViewGroup.");
            }
            ViewGroup mParent = (ViewGroup) mNormalView.getParent();
            View.inflate(this, R.layout.layout_loading, mParent);
            View.inflate(this, R.layout.layout_error, mParent);
            mLoadingView = findViewById(R.id.loading_view);
            mErrorView = findViewById(R.id.error_view);
            mErrorView.findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reload();
                }
            });
            mErrorView.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.VISIBLE);
            mNormalView.setVisibility(View.GONE);
        }
    }

    /**
     * 重新加载
     */
    protected abstract void reload();

    /**
     * 显示加载中布局
     */
    protected void showLoadingView() {
        if(mCurrentState == LOADING_STATE){
            return;
        }
        hideView();
        mCurrentState = LOADING_STATE;
        if(mLoadingView != null)
            mLoadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示错误布局
     */
    protected void showErrorView() {
        if(mCurrentState == ERROR_STATE){
            return;
        }
        hideView();
        mCurrentState = ERROR_STATE;
        if(mErrorView != null)
            mErrorView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示正常布局
     */
    protected void showNormalView() {
        if(mCurrentState == NORMAL_STATE){
            return;
        }
        hideView();
        mCurrentState = NORMAL_STATE;
        if (mNormalView != null) {
            mNormalView.setAlpha(0);
            mNormalView.setVisibility(View.VISIBLE);
            mNormalView.animate().setDuration(300).alpha(1);
        }
    }

    /**
     * 隐藏布局
     */
    protected void hideView(){
        switch (mCurrentState){
            case NORMAL_STATE:
                if(mNormalView != null) {
                    mNormalView.setAlpha(1);
                    mNormalView.animate().setDuration(300).alpha(0);
                    mNormalView.setVisibility(View.GONE);
                }
                break;
            case LOADING_STATE:
                if(mLoadingView != null)
                    mLoadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                if(mErrorView != null)
                    mErrorView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 跳转到登录
     */
    protected void jumpToLogin(){
        JumpUtil.jumpToLogin(curActivity);
    }

    /**
     * 设置下拉刷新 上拉加载
     * @param refresh 刷新布局
     * @param adapter 适配器 如果只需要下拉刷新 只需要将adapter传入空
     * @param listener 方法回调
     */
    protected void initRefresh(SmartRefreshLayout refresh, BaseQuickAdapter adapter, OnCommonRefreshLoadMoreListener listener){
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                listener.onRefresh();
            }
        });
        if(!(refresh.getChildAt(0) instanceof RecyclerView))
            return;
        if(adapter != null) {
            adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    listener.onLoadMore();
                }
            });
        }
    }

    protected void setRefreshStatus(SmartRefreshLayout refresh, BaseQuickAdapter adapter, List list){
        if(refresh.getState().isOpening)
            refresh.finishRefresh();
        if(adapter != null) {
            if (list != null && list.size() >= BaseConstants.limit)
                adapter.getLoadMoreModule().loadMoreComplete();
            else
                adapter.getLoadMoreModule().loadMoreEnd();
        }
        showNormalView();
    }

    protected void setEmptyView(BaseQuickAdapter adapter, boolean hasHeader, String str){
//        View view
    }

}
