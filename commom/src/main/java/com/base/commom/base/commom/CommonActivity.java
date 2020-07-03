package com.base.commom.base.commom;

import com.base.commom.base.activity.BaseActivity;
import com.base.commom.mvp.IBaseContract;

public abstract class CommonActivity<P extends ICommonContract.Presenter> extends BaseActivity<P> implements ICommonContract.View {

    @Override
    public void attentionCommunitySuccess(int b) {

    }

    @Override
    public void attentionCancelCommunitySuccess(int b) {

    }

    @Override
    public void likeSuccess() {

    }

    @Override
    public void likeCancelSuccess() {

    }

    @Override
    public void attentionSuccess() {

    }

    @Override
    public void attentionCancelSuccess() {

    }

    @Override
    public void collectCancelSuccess() {

    }

    @Override
    public void collectSuccess() {

    }
}
