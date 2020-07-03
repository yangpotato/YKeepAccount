package com.base.commom.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.base.commom.R;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

public class BaseCenterPopup extends CenterPopupView {
    private String title;
    private String content, rightSubmit;
    public BaseCenterPopup(@NonNull Context context) {
        super(context);
    }

    public BaseCenterPopup(@NonNull Context context, String title, OnInputConfirmListener inputConfirmListener) {
        this(context);
        this.title = title;
        this.inputConfirmListener = inputConfirmListener;
    }

    public BaseCenterPopup setInputConfirmListener(OnInputConfirmListener inputConfirmListener) {
        this.inputConfirmListener = inputConfirmListener;
        return this;
    }

    public BaseCenterPopup setTitle(String title) {
        this.title = title;
        return this;
    }

    public BaseCenterPopup setContent(String content) {
        this.content = content;
        return this;
    }

    public BaseCenterPopup setRightSubmit(String rightSubmit) {
        this.rightSubmit = rightSubmit;
        return this;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_base;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        TextView tvContent = findViewById(R.id.tv_content);
        tvContent.setText(content);
        TextView tvSure = findViewById(R.id.btn_sure);
        if(!TextUtils.isEmpty(rightSubmit))
            tvSure.setText(rightSubmit);
        findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.btn_sure).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               if(inputConfirmListener != null)
                   inputConfirmListener.onConfirm(title);
                dismiss();
            }
        });
    }

    private OnInputConfirmListener inputConfirmListener;

}
