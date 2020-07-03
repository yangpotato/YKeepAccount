package com.base.commom.base.commom;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;

public interface BaseRVHelper<T> {

    void initRecyclerView(BaseQuickAdapter adapter, RecyclerView recyclerView);

    void initRecyclerView(BaseQuickAdapter adapter, RecyclerView recyclerView, RecyclerView.LayoutManager manager);

    void initRefresh(SmartRefreshLayout refreshLayout);

    void initRecyclerViewAndRefresh(BaseQuickAdapter adapter, RecyclerView recyclerView, SmartRefreshLayout refreshLayout);

    View initEmptyView(RecyclerView recyclerView);

    View initHeaderView(RecyclerView recyclerView);

    View initFooterView(RecyclerView recyclerView);

    void setData(List<T> list, int count, boolean isLoadMore);

    void onItemClick(View view, T t, int position);

    void onItemChildClick(View view, T t, int position);
}
