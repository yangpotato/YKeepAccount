package com.base.commom.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseSupportFragment extends Fragment {
    protected Activity curActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        curActivity = getActivity();
        initViewStatue(view);
        onFragmentViewCreate();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment(savedInstanceState);
    }

    /**
     * 获取布局文件
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void onFragmentViewCreate();

    protected abstract void initFragment(Bundle savedInstanceState);

    protected abstract void initViewStatue(View view);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
