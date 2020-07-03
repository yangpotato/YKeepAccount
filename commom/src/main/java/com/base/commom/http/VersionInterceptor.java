package com.base.commom.http;

import com.base.commom.BaseConstants;
import com.base.commom.utils.LogUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class VersionInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtil.i("version->" + BaseConstants.version);
        if (request.method().equals("GET")) {
            //添加公共参数
            HttpUrl httpUrl = request.url()
                    .newBuilder()
                    .setQueryParameter("version", BaseConstants.version)
                    .build();
            request = request.newBuilder().url(httpUrl).build();
        } else if (request.method().equals("POST")) {
            if (request.body() instanceof FormBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();
                //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
                for (int i = 0; i < formBody.size(); i++) {
                    String key = formBody.encodedName(i);
                    String value = formBody.encodedValue(i);
                    bodyBuilder.addEncoded(key, value);
                }
                 formBody = bodyBuilder
                        .addEncoded("version", BaseConstants.version)
                        .build();
                request = request.newBuilder().post(formBody).build();
            }
        }

        return chain.proceed(request);
    }

}

