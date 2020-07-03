package com.base.commom.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.base.commom.BaseConstants;
import com.base.commom.R;

public class JumpUtil {
    public static String P1 = "p1";
    public static String P2 = "p2";
    public static String P3 = "p3";
    public static String P4 = "p4";

    public static final String LOGIN_CLASS_NAME = "com.yiwang.fb.login.LoginActivity";
    public static final int CODE = 1001;
    public static final int CODE_1001 = 1001;
    public static final int CODE_1002 = 1002;

    public  static void overlay(Context context, Class<? extends Activity> targetClazz){
        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public  static void overlay(Context context, Class<? extends Activity> targetClazz, int type){
        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(JumpUtil.P1, type);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public  static void overlay(Context context, Class<? extends Activity> targetClazz, int n1, int n2){
        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(JumpUtil.P1, n1);
        mIntent.putExtra(JumpUtil.P2, n2);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public  static void overlay(Context context, Class<? extends Activity> targetClazz, String n1, int n2){
        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(JumpUtil.P1, n1);
        mIntent.putExtra(JumpUtil.P2, n2);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public  static void overlay(Context context, Class<? extends Activity> targetClazz, int n1, String n2){
        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(JumpUtil.P1, n1);
        mIntent.putExtra(JumpUtil.P2, n2);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public  static void overlay(Context context, Class<? extends Activity> targetClazz, String p1){
        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(JumpUtil.P1, p1);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public  static void overlay(Context context, Class<? extends Activity> targetClazz, Bundle bundle){
        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(JumpUtil.P1, bundle);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void jumpToLogin(Activity context){
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.setClassName(context, LOGIN_CLASS_NAME);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        context.finish();
    }

    public static void overlayWithClear(Context context, Class<? extends Activity> targetClazz){
        Intent mIntent = new Intent(context, targetClazz);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void overlayWithClassName(Context context, String className){
        Intent mIntent = new Intent();
        mIntent.setClassName(context, className);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void overlayWithClassName(Context context, String className, int p1){
        Intent mIntent = new Intent();
        mIntent.setClassName(context, className);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(JumpUtil.P1, p1);
        context.startActivity(mIntent);
        if(context instanceof Activity)
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void loginOut(Context context){
        BaseConstants.token = "";
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.setClassName(context, LOGIN_CLASS_NAME);
        context.startActivity(mIntent);
    }

    public  static void overlayForResult(Activity context, Class<? extends Activity> targetClazz){
        overlayForResult(context, targetClazz, null);
    }

    public  static void overlayForResult(Activity context, Class<? extends Activity> targetClazz, Bundle bundle){
        overlayForResult(context, targetClazz, CODE, bundle);
    }

    public  static void overlayForResult(Activity context, Class<? extends Activity> targetClazz, int code){
        overlayForResult(context, targetClazz, code, null);
    }

    public  static void overlayForResult(Activity context, Class<? extends Activity> targetClazz, int code, Bundle bundle){
        Intent mIntent = new Intent(context, targetClazz);
//        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null)
            mIntent.putExtra(JumpUtil.P1, bundle);
        context.startActivityForResult(mIntent, code);
        context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
