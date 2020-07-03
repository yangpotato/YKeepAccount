package com.base.commom.http;

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.tencent.bugly.crashreport.CrashReport;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.text.ParseException;

import retrofit2.HttpException;

public class ExceptionHandler {
    public static final int NOT_LOGIN = 1000;

    public static String handleException(Throwable e) {
        String errmsg = "网络连接异常,请稍后重试";
        if (e instanceof HttpException) {
            errmsg = "网络错误";
        }else if(e instanceof ApiException){
            errmsg = ((ApiException) e).getErrmsg();
        }else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            errmsg = "解析错误";
        } else if (e instanceof ConnectException) {
            errmsg = "网络连接失败,请稍后重试";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            e.printStackTrace();
            errmsg = "证书验证失败";
        } else if (e instanceof ConnectTimeoutException) {
            errmsg = "网络连接超时";
        } else if (e instanceof java.net.SocketTimeoutException) {
            errmsg = "连接超时";
        } else {
            //将异常山上报给bugly
            CrashReport.postCatchedException(e);
            errmsg = "网络连接异常,请稍后重试";
        }
        return errmsg;
    }
}
