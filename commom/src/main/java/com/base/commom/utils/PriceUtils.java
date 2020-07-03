package com.base.commom.utils;

import android.text.TextUtils;

public class PriceUtils {
    public static String getPrice(String price) {
        String priceResult = "0";
        if (TextUtils.isEmpty(price))

          return    "0";

        if (price.contains("."))
            priceResult = Double.parseDouble(price) / 100 + "";
        else
            priceResult = Integer.parseInt(price) / 100.00 + "";

        if (priceResult.contains(".")&&priceResult.split("\\.").length>1&&priceResult.split("\\.")[1].length()<2)
            priceResult=priceResult+"0";

        return priceResult;
    }

    public static String getIntPrice(String price) {
        double priceResult = 0;

        if (price.contains("."))
            priceResult = Double.parseDouble(price) / 100 ;
        else
            priceResult = Integer.parseInt(price) / 100.00;

        return  (int)priceResult+"";
    }

    public static String getIntegral(long number){
        String integral = "0";
        if(number == 0)
            return "免费";
        if(number < 10000)
            integral = number + "";
        else
            integral = ((double)number / 10000) + "万";
        return integral;
    }
}
