package com.base.commom.utils;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.DrawableRes;

/**
 * @author 1one
 * @date 2019/9/2.
 */
public class DrawableUtils {

    public static GradientDrawable getGradientDrawable(int radius, String color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(radius);
        drawable.setColor(Color.parseColor(color));
        return drawable;
    }

    public static GradientDrawable getGradientDrawable(int radius, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(radius);
        drawable.setColor(color);
        return drawable;
    }

    public static String getUriByDrawableRes(Context context, @DrawableRes int id){
        return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(id) + "/" +
                context.getResources().getResourceTypeName(id) + "/" +
                context.getResources().getResourceEntryName(id);
    }
}
