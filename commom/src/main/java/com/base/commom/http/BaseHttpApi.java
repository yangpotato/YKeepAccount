package com.base.commom.http;

import com.base.commom.entity.SuccessEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseHttpApi {

    /**
     * 购买礼物
     */
    @POST("integral_goods/buy")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessEntity>> buyIntegralGoods(@FieldMap Map<String, Object> map);

    /**
     * 赠送礼物
     */
    @POST("present/gift")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessEntity>> giveGift(@FieldMap Map<String, Object> map);


    /**
     * 赠送任务礼物
     */
    @POST("task/taskreward")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessEntity>> giveTaskGift(@FieldMap Map<String, Object> map);
}
