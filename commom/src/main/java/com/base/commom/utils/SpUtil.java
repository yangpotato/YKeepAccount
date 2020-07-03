package com.base.commom.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xzz on 2016/1/11.
 **/
public class SpUtil {

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("NodeNormal", Context.MODE_PRIVATE);
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(key, 0);
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(key, defValue);
    }

    /**
     * 从首选项中获取String值,默认值为null
     *
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(key, null);
    }

    public static String getString(String key, Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(key, null);
    }

    /**
     * 从首选项中获取boolean值,默认值为false
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(key, false);
    }

    public static boolean getBoolean(Context context, String key,boolean defauleValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(key, defauleValue);
    }

    /**
     * 向首选项中存储数据(仅限于String,int,boolean三种数据类型)
     */
    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.apply();
    }

}