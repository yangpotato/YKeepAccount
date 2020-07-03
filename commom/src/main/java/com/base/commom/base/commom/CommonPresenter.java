package com.base.commom.base.commom;

import com.base.commom.entity.SuccessEntity;
import com.base.commom.entity.SuccessIntEntity;
import com.base.commom.http.BaseObserver;
import com.base.commom.mvp.BasePresenter;

public abstract class CommonPresenter<V extends ICommonContract.View> extends BasePresenter<V> implements ICommonContract.Presenter<V> {
    @Override
    public void attentionCommunity(int community_id) {
        addSubscribe(create(CommonApi.class).attentionCommunity(community_id), new BaseObserver<SuccessIntEntity>(mView, true) {
            @Override
            protected void onSuccess(SuccessIntEntity data) {
                mView.attentionCommunitySuccess(data.getIssuccess());
            }
        });
    }

    @Override
    public void attentionCancelCommunity(int community_id) {
        addSubscribe(create(CommonApi.class).attentionCancelCommunity(community_id), new BaseObserver<SuccessIntEntity>(mView, true) {
            @Override
            protected void onSuccess(SuccessIntEntity data) {
                mView.attentionCancelCommunitySuccess(data.getIssuccess());
            }
        });
    }

    @Override
    public void like(int dynamic_id) {
        addSubscribe(create(CommonApi.class).like(dynamic_id), new BaseObserver<SuccessEntity>(mView, true) {
            @Override
            protected void onSuccess(SuccessEntity data) {
                mView.likeSuccess();
            }
        });
    }

    @Override
    public void likeCancel(int dynamic_id) {
        addSubscribe(create(CommonApi.class).likeCancel(dynamic_id), new BaseObserver<SuccessEntity>(mView, true) {
            @Override
            protected void onSuccess(SuccessEntity data) {
                mView.likeCancelSuccess();
            }
        });
    }

    @Override
    public void attention(int member_id) {
        addSubscribe(create(CommonApi.class).attention(member_id), new BaseObserver<SuccessEntity>(mView, true) {
            @Override
            protected void onSuccess(SuccessEntity data) {
                mView.attentionSuccess();
            }
        });
    }

    @Override
    public void attentionCancel(int member_id) {
        addSubscribe(create(CommonApi.class).attentionCancel(member_id), new BaseObserver<SuccessEntity>(mView, true) {
            @Override
            protected void onSuccess(SuccessEntity data) {
                mView.attentionCancelSuccess();
            }
        });
    }

    @Override
    public void collect(String resourceid, int type) {

    }

    @Override
    public void collectCancel(String[] resourceids, int type) {

    }
}
