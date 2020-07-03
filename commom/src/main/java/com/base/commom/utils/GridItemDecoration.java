package com.base.commom.utils;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount; //列数
    private int spacing; //间隔
    private boolean includeEdge; //是否包含边缘
    private int headerCount = 0;

    public GridItemDecoration(int spanCount, int spacing, boolean includeEdge, int headerCount) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        this.headerCount = headerCount;
    }

    public GridItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //这里是关键，需要根据你有几列来判断
        int position = parent.getChildAdapterPosition(view); // item position

        if(position < headerCount){
            Log.e("YPOTATO", position + "里面");
            outRect.left = 0;
            outRect.top = 0;
            outRect.right = 0;
            outRect.bottom = 0;
            return;
        }
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount - headerCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount - headerCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}
