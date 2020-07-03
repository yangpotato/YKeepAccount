package com.base.commom.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int defultSpace;
    private int firstSpace;
    private int topSpace;

    public SpaceItemDecoration(int first) {
        defultSpace = DensityUtils.dip2px(25);
//        firstSpace = dp2px(context, 100);
        topSpace = DensityUtils.dip2px(10);
        this.firstSpace = first;
    }

    public SpaceItemDecoration(int defultSpace, int firstSpace, int topSpace) {
        this.defultSpace = defultSpace;
        this.firstSpace = firstSpace;
        this.topSpace = topSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        if(position == 0){
            outRect.left = firstSpace;
        }else{
            outRect.left = defultSpace / 2;
        }
        if(position == parent.getAdapter().getItemCount()- 1){
            outRect.right = firstSpace;
        }else {
            outRect.right = defultSpace / 2;
        }
        outRect.top = topSpace;
    }
}
