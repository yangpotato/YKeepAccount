package com.base.commom.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

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
}
