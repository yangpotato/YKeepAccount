package com.base.commom.base.fragment;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.commom.BaseConstants;
import com.base.commom.R;
import com.base.commom.base.activity.BaseActivity;
import com.base.commom.base.activity.BaseRVActivity;
import com.base.commom.base.commom.BaseRVHelper;
import com.base.commom.mvp.IBaseContract;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRvFragment <P extends IBaseContract.Presenter, T> extends BaseFragment<P> implements BaseRVHelper<T> {
    protected RecyclerView mRecyclerView;
    protected SmartRefreshLayout mRefreshLayout;
    protected List<T> mList = new ArrayList<>();
    protected BaseQuickAdapter mAdapter;


    @Override
    public void initRecyclerView(BaseQuickAdapter adapter, RecyclerView recyclerView) {
        initRecyclerView(adapter, recyclerView, new LinearLayoutManager(curActivity));
    }

    @Override
    public void initRecyclerView(BaseQuickAdapter adapter, RecyclerView recyclerView, RecyclerView.LayoutManager manager) {
        this.mRecyclerView = recyclerView;
        this.mAdapter = adapter;

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        View emptyView = initEmptyView(mRecyclerView);
        if(emptyView != null)
            mAdapter.setEmptyView(emptyView);
        View headerView = initHeaderView(mRecyclerView);
        if(headerView != null)
            mAdapter.addHeaderView(headerView);
        View footerView = initFooterView(mRecyclerView);
        if(footerView != null)
            mAdapter.addFooterView(footerView);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                BaseRvFragment.this.onItemClick(view, mList.get(position), position);
            }
        });
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                BaseRvFragment.this.onItemChildClick(view, mList.get(position), position);
            }
        });
    }

    @Override
    public void initRecyclerViewAndRefresh(BaseQuickAdapter adapter, RecyclerView recyclerView, SmartRefreshLayout refreshLayout) {
        initRecyclerView(adapter, recyclerView, new LinearLayoutManager(curActivity));
        initRefresh(refreshLayout);
    }

    @Override
    public void initRefresh(SmartRefreshLayout refreshLayout) {
        this.mRefreshLayout = refreshLayout;
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(mAdapter.getLoadMoreModule().isLoading()){
                    showMessage("请先等待加载完成");
                    mRefreshLayout.finishRefresh(false);
                    return;
                }
                BaseRvFragment.this.onRefresh();
            }
        });
        if(!(mRefreshLayout.getChildAt(0) instanceof RecyclerView))
            return;
        if(mAdapter != null) {
            mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    onLoadMore();
                }
            });
        }
    }

    @Override
    public void onItemChildClick(View view, T t, int position) {

    }

    /**
     * 加载更多方法
     */
    protected abstract void onLoadMore();

    /**
     * 下拉刷新方法
     */
    protected abstract void onRefresh();

    @Override
    public View initEmptyView(RecyclerView recyclerView) {
        return LayoutInflater.from(curActivity).inflate(R.layout.layout_empty, recyclerView, false);
    }

    @Override
    public View initHeaderView(RecyclerView recyclerView) {
        return null;
    }

    @Override
    public View initFooterView(RecyclerView recyclerView) {
        return null;
    }

    @Override
    public void setList(List list, int count, boolean isLoadMore) {
        setData(list, count, isLoadMore);
    }

    @Override
    public void setData(List<T> list, int count, boolean isLoadMore) {
        if(mRefreshLayout != null) {
            if (mRefreshLayout.getState().isOpening)
                mRefreshLayout.finishRefresh();
        }
        if(mAdapter != null && list != null) {
            if(isLoadMore){
                mList.addAll(list);
                mAdapter.addData(list);
            }else {
                mList = list;
                mAdapter.setNewInstance(list);
            }
            if (list.size() >= BaseConstants.limit)
                mAdapter.getLoadMoreModule().loadMoreComplete();
            else
                mAdapter.getLoadMoreModule().loadMoreEnd();
        }else if(mAdapter != null){
            mAdapter.getLoadMoreModule().loadMoreEnd();
        }
        showNormalView();
    }
}
