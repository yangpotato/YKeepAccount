package com.base.commom;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;


import com.base.commom.utils.DensityUtils;
import com.base.commom.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
//import com.tencent.bugly.crashreport.CrashReport;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 1one
 * @date 2019/8/28.
 */
public class AppApplication extends Application {
    protected static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        XPopup.setAnimationDuration(300);
        application = this;
        DensityUtils.density = getResources().getDisplayMetrics().density;
//        CrashReport.initCrashReport(getApplicationContext(), "abd6962114", false);
        ToastUtils.init(this);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
    }

    public static Application getInstance(){
        return application;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.white, R.color.color666);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
    }
}
