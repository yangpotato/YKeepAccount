package com.base.commom.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.base.commom.R;
import com.lxj.xpopup.core.BottomPopupView;


public class PickerImagePopup extends BottomPopupView implements View.OnClickListener {
    private TextView tvCamera, tvAlbum, tvCancel;
    private OnPickerVImageClickListener mListener;

    public void setmListener(OnPickerVImageClickListener mListener) {
        this.mListener = mListener;
    }

    public PickerImagePopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_picker_image;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tvCamera = findViewById(R.id.tv_camera);
        tvAlbum = findViewById(R.id.tv_album);
        tvCancel = findViewById(R.id.tv_cancel);
        tvCamera.setOnClickListener(this);
        tvAlbum.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.tv_camera){
//
//        }else if(v.getId() == R.id.tv_album){
//            if(mListener != null)
//                mListener.onClick(v.getId());
//        }else if(v.getId() == R.id.tv_cancel){
//            dismiss();
//        }
        if (v.getId() == R.id.tv_cancel) {
            dismiss();
        } else {
            if (mListener != null)
                mListener.onClick(v.getId());
        }
    }

    public interface OnPickerVImageClickListener {
        void onClick(int id);
    }
}
