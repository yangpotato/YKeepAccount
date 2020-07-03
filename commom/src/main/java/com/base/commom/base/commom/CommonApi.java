package com.base.commom.base.commom;

import com.base.commom.entity.SuccessEntity;
import com.base.commom.entity.SuccessIntEntity;
import com.base.commom.http.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommonApi {

    //关注社群
    @POST("community/attention")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessIntEntity>> attentionCommunity(@Field("community_id") int community_id);

    //取消关注社群
    @POST("community/cancel/attention")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessIntEntity>> attentionCancelCommunity(@Field("community_id") int community_id);

    //点赞
    @POST("social/like")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessEntity>> like(@Field("dynamic_id") int dynamic_id);

    //取消点赞
    @POST("social/cancel/like")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessEntity>> likeCancel(@Field("dynamic_id") int dynamic_id);

    //关注
    @POST("social/attention")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessEntity>> attention(@Field("member_id") int member_id);

    //取消关注
    @POST("social/cancel/attention")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessEntity>> attentionCancel(@Field("member_id") int member_id);

    //添加收藏
    @POST("collection/add")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessEntity>> collect(@Field("resourceid") String resourceid,
                                                    @Field("type") int type);

    //取消收藏
    @POST("collection/add")
    @FormUrlEncoded
    Observable<BaseResponse<SuccessEntity>> collectCancel(@Field("resourceids") String[] resourceids,
                                                    @Field("type") int type);
}
