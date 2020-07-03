package com.base.commom.base.commom;

import androidx.annotation.NonNull;

import com.base.commom.mvp.IBaseContract;

public interface ICommonContract {

    interface View extends IBaseContract.View{
        void attentionCommunitySuccess(int b);

        void attentionCancelCommunitySuccess(int b);

        void likeSuccess();

        void likeCancelSuccess();

        void attentionSuccess();

        void attentionCancelSuccess();

        void collectSuccess();

        void collectCancelSuccess();
    }

    interface Presenter<V extends ICommonContract.View> extends IBaseContract.Presenter<V>{
        void attentionCommunity(int community_id);

        void attentionCancelCommunity(int community_id);

        void like(int dynamic_id);

        void likeCancel(int dynamic_id);

        void attention(int member_id);

        void attentionCancel(int member_id);

        void collect(String resourceid, int type);

        void collectCancel(String[] resourceids, int type);

    }
}
