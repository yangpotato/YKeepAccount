package com.base.commom.base.fragment;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.base.commom.BaseConstants;
import com.base.commom.R;
import com.base.commom.base.activity.BaseSupportActivity;
import com.base.commom.listener.OnCommonRefreshLoadMoreListener;
import com.base.commom.utils.JumpUtil;
import com.base.commom.utils.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

public abstract class BaseRootFragment extends BaseLazyFragment {
    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;

    protected View mNormalView, mLoadingView, mErrorView;
    private int mCurrentState = LOADING_STATE;

    @Override
    protected void initViewStatue(View view) {
        if (view == null)
            return;
        mNormalView = view.findViewById(R.id.normal);
        if (mNormalView != null) {
            if (!(mNormalView.getParent() instanceof ViewGroup)) {
                throw new IllegalStateException(
                        "mNormalView's ParentView should be a ViewGroup.");
            }
            ViewGroup mParent = (ViewGroup) mNormalView.getParent();
            View.inflate(getActivity(), getLoadingViewLayout() != 0 ? getLoadingViewLayout() : R.layout.layout_loading, mParent);
            View.inflate(getActivity(), getErrorViewLayout() != 0 ? getErrorViewLayout() : R.layout.layout_error, mParent);
            mLoadingView = view.findViewById(R.id.loading_view);
            mErrorView = view.findViewById(R.id.error_view);
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
     * 设置加载布局
     *
     * @return
     */
    protected int getLoadingViewLayout() {
        return 0;
    }

    /**
     * 设置错误布局
     * 父布局必须设置id为error_view
     * @return
     */
    protected int getErrorViewLayout() {
        return 0;
    }

    /**
     * 重新加载
     */
    protected abstract void reload();

    /**
     * 显示加载中布局
     */
    protected void showLoadingView() {
        if (mCurrentState == LOADING_STATE) {
            return;
        }
        hideView();
        mCurrentState = LOADING_STATE;
        if (mLoadingView != null)
            mLoadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示错误布局
     */
    protected void showErrorView() {
        if (mCurrentState == ERROR_STATE) {
            return;
        }
        hideView();
        mCurrentState = ERROR_STATE;
        if (mErrorView != null)
            mErrorView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示正常布局
     */
    protected void showNormalView() {
        if (mCurrentState == NORMAL_STATE) {
            return;
        }
        hideView();
        mCurrentState = NORMAL_STATE;
        if (mNormalView != null) {
            mNormalView.setAlpha(0);
            mNormalView.setVisibility(View.VISIBLE);
            mNormalView.animate().setDuration(500).alpha(1);
        }
    }

    /**
     * 隐藏布局
     */
    private void hideView() {
        switch (mCurrentState) {
            case NORMAL_STATE:
                if (mNormalView != null)
                    mNormalView.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                if (mLoadingView != null)
                    mLoadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                if (mErrorView != null)
                    mErrorView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 跳转到登录
     */
    protected void jumpToLogin() {
        JumpUtil.jumpToLogin(curActivity);
    }


    /**
     * 设置下拉刷新 上拉加载
     *
     * @param refresh  刷新布局
     * @param adapter  适配器 如果只需要下拉刷新 只需要将adapter传入空
     * @param listener 方法回调
     */
    protected void initRefresh(SmartRefreshLayout refresh, BaseQuickAdapter adapter, OnCommonRefreshLoadMoreListener listener) {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                listener.onRefresh();
            }
        });
        if (!(refresh.getChildAt(0) instanceof RecyclerView))
            return;
        if (adapter != null) {
            adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    listener.onLoadMore();
                }
            });
        }
    }

    protected void setRefreshStatus(SmartRefreshLayout refresh, BaseQuickAdapter adapter, List list) {
        if (refresh.getState().isOpening)
            refresh.finishRefresh();
        if (adapter != null) {
            if (list != null && list.size() >= BaseConstants.limit)
                adapter.getLoadMoreModule().loadMoreComplete();
            else
                adapter.getLoadMoreModule().loadMoreEnd();
        }
        showNormalView();
    }

    protected void setRefreshStatus(SmartRefreshLayout refresh, BaseQuickAdapter adapter, List list, int limit) {
        if (refresh.getState().isOpening)
            refresh.finishRefresh();
        if (adapter != null) {
            if (list != null && list.size() >= (limit == 0 ? BaseConstants.limit : limit))
                adapter.getLoadMoreModule().loadMoreComplete();
            else
                adapter.getLoadMoreModule().loadMoreEnd();
        }
        showNormalView();
    }

}
