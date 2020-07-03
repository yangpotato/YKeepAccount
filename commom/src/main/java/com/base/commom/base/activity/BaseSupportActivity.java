package com.base.commom.base.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.appcompat.app.AppCompatActivity;

import com.base.commom.AppApplication;
import com.base.commom.BaseConstants;
import com.base.commom.R;
import com.base.commom.utils.ActivityCacheUtil;
import com.base.commom.utils.LogUtil;

public abstract class BaseSupportActivity extends AppCompatActivity {
    protected BaseSupportActivity curActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        ActivityCacheUtil.addActivity(this);
        curActivity = this;
        initViewStatue();
        onViewCreated();
        initToolbar();
        initActivity();
    }

    /**
     * 初始化布局
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initViewStatue();

    protected abstract void initToolbar();

    protected abstract void onViewCreated();

    protected abstract void initActivity();


    public BaseSupportActivity getCurActivity() {
        return curActivity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCacheUtil.removeActivity(this);
        if (this.equals(curActivity)) {
            curActivity = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        curActivity = this;
    }

    protected BaseSupportActivity getActivity() {
        return this;
    }

    protected AppApplication getAppApplication() {
        return (AppApplication) getApplication();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void finishWithAnim(){
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        if (newConfig.fontScale != 1)
//            getResources();
//        super.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        if (res.getConfiguration().fontScale != 1) {
//            Configuration newConfig = new Configuration();
//            newConfig.setToDefaults();
//            res.updateConfiguration(newConfig, res.getDisplayMetrics());
//        }
//        return res;
//    }
}
