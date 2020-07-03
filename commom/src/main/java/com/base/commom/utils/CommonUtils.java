package com.base.commom.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class CommonUtils {

    /**
     * 截取输入字符串 大于6个后显示...
     * @param str
     * @return
     */
    public static String getStringCount(int count, String str) {
        try {
            if(TextUtils.isEmpty(str))
                str = "无昵称";
            String symbol = "...";
            int len = count * 2;
            int counterOfDoubleByte = 0;
            byte b[] = str.getBytes("GBK");
            if (b.length <= len)
                return str;
            for (int i = 0; i < len; i++) {
                if (b[i] < 0)
                    counterOfDoubleByte++;
            }
            if (counterOfDoubleByte % 2 == 0)
                return new String(b, 0, len, "GBK") + symbol;
            else
                return new String(b, 0, len - 1, "GBK") + symbol;

        } catch (UnsupportedEncodingException e) {
            LogUtil.e("截取字符串getStringCount异常："+ e.toString());
            return "";
        }
    }

    public static String deletePointZero(String str){
        if(str.indexOf(".") > 0){
            //正则表达
            str = str.replaceAll("0+?$", "");//去掉后面无用的零
            str = str.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return str;
    }

    public static String changeDistance(String distance){
        if(TextUtils.isEmpty(distance))
            return "0m";
        String unit = "m";
        try {
            float distanceF = Float.parseFloat(distance);
            if(distanceF > 1000) {
                distanceF = distanceF / 1000;
                unit = "km";
            }
            DecimalFormat df = new DecimalFormat("0.00");
            return deletePointZero(df.format(distanceF)) + unit;
        }catch (NumberFormatException e){
            return distance;
        }

    }

    public static String formatTenThousand(String number){
        try {
            return formatTenThousand(Integer.parseInt(number));
        }catch (NumberFormatException e) {
            return "0";
        }
    }

    @SuppressLint("DefaultLocale")
    public static String formatTenThousand(int number){
        if(number < 10000)
            return number + "";
        return deletePointZero(String.format("%.2f", (float)number / 10000)) + "万";
    }

    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    public static int getSplit(String str, String regex){
        int result = -1;
        String[] strs = str.split("_");
        try {
            result = Integer.parseInt(strs[strs.length - 1]);
            return result;
        }catch (NumberFormatException e) {
            return 0;
        }
    }


    /**
     * 获取版本名称
     *
     * @param context 上下文
     *
     * @return 版本名称
     */
    public static String getVersionName(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     *
     * @return 版本号
     */
    public static long getVersionCode(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }

}
