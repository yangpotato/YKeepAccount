package com.base.commom;

public class BaseConstants {
    public static String token = "";
    public static String version = "";
    public static String failuretime = "";
    public static String BASE_WEB_URL_DEBUG = "http://nonchat.ketao.com";
    public static String BASE_WEB_URL_RELEASE = "http://feelbt.com";
    public static String BASE_WEB_URL = BuildConfig.DEBUG ? BASE_WEB_URL_DEBUG : BASE_WEB_URL_RELEASE;
    public static String BASE_URL = BASE_WEB_URL + "/api/";

    public static final String BASE_H5_URL = BASE_WEB_URL;
    public static final String COMMUNITY_SHOP_URL = BASE_H5_URL + "/#/my/store";
    public static final String CART_URL = BASE_H5_URL + "/#/my/cart";
    public static final String ORDER_URL = BASE_H5_URL + "/#/order/list/1";
    public static final String GOOD_DETAIL = BASE_H5_URL + "/#/shopdetail?source=ORDINARY";
    public static final String OIL_RECHARGE = BASE_H5_URL + "/#/rechargeCard/rechargeCard?type=3";
    public static final String BILL_RECHARGE = BASE_H5_URL + "/#/rechargeCard/rechargeCard?type=2";
    public static final String SHARE_REGISTER = BASE_H5_URL + "/#/my/my_friendout/";
    public static int limit = 10;

    public static final int WECHATPAY = 0;
    public static final int ALIPAY = 1;

    public static String latitude = "";
    public static String longitude = "";
    public static String province = "";
    public static String city = "";
    public static String area = "";
    public static String country = "";
    public static String address = "";


    public static boolean hasSetLocation = false;

    public static boolean auto_video = true;

}
