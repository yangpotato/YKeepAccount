package com.base.commom.utils;

import android.content.Context;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;

public class ToastUtil {
    public static Toast mToast;

    public static void show(Context context, String str){
//        if(mToast == null) {
//            mToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
//        }
//        mToast.setText(str);
//        mToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
//        mToast.show();
        ToastUtils.show(str);
    }
    public static void show(String str){
        ToastUtils.show(str);
    }
}
