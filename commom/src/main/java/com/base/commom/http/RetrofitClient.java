package com.base.commom.http;

import com.base.commom.BaseConstants;
import com.base.commom.utils.LogUtil;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mRetrofitClient;
    private static OkHttpClient mOKOkHttpClient;
    private static Retrofit mRetrofit;

    public RetrofitClient() {
        mOKOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
//                .addInterceptor(new HttpJsonLogInterceptor(new HttpLoggingInterceptor.Logger() {
//                    @Override
//                    public void log(String message) {
//                        LogUtil.i("OkGo", message);
//                    }
//                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new VersionInterceptor())
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        LogUtil.i("OkGo", message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BaseConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOKOkHttpClient)
                .build();
    }

    public static RetrofitClient getInstance(){
        if(mRetrofitClient == null){
            synchronized (RetrofitClient.class){
                if(mRetrofitClient == null)
                    mRetrofitClient = new RetrofitClient();
            }
        }
        return mRetrofitClient;
    }

    public OkHttpClient getOkHttpClient(){
        return mOKOkHttpClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }


    public MultipartBody uploadListFile(String key, List<File> list){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(File file : list){
            builder.addFormDataPart(key +"[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        return builder.build();
    }

    public MultipartBody uploadFile(String key, File file){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        return builder.build();
    }

    public List<MultipartBody.Part> uploadFile(int type, File file){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("token", BaseConstants.token);
        builder.addFormDataPart("type", "1");
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), imageBody);
        List<MultipartBody.Part> parts = builder.build().parts();
        return parts;
    }


}
