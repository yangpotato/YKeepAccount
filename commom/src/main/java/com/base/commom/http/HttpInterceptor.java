package com.base.commom.http;


import com.base.commom.BaseConstants;
import com.base.commom.utils.LogUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {
    private StringBuilder params;
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtil.i("token->" + BaseConstants.token);
        params = new StringBuilder("\n");
        if (request.method().equals("GET")) {
            //添加公共参数
            HttpUrl httpUrl = request.url()
                    .newBuilder()
                    .setQueryParameter("token", BaseConstants.token)
                    .build();
            request = request.newBuilder().url(httpUrl).build();
        }else if (request.method().equals("POST")) {
            params.append(request.url().toString()).append("\n");
            if (request.body() instanceof FormBody) {
                //检测参数列表是否含有token， 以防参数里有两个token
                boolean hasToken = false;
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();

                //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
                for (int i = 0; i < formBody.size(); i++) {
                    String key = formBody.encodedName(i);
                    String value = formBody.encodedValue(i);
                    bodyBuilder.addEncoded(key, value);
                    params.append(key).append(":").append(value).append("\n");
                    if("token".equals(key))
                        hasToken = true;
                }

                if(hasToken) {
                    formBody = bodyBuilder
                            .build();
                }else {
                    formBody = bodyBuilder
                            .addEncoded("token", BaseConstants.token)
                            .build();
                    params.append("token").append(":").append(BaseConstants.token).append("\n");
                }
                LogUtil.i("OkGo", "params:" + params);
                request = request.newBuilder().post(formBody).build();
            }
        }

        return chain.proceed(request);
    }

}

