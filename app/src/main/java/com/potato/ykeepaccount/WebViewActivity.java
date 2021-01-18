package com.potato.ykeepaccount;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.base.commom.base.activity.BaseActivity;
import com.base.commom.mvp.IBaseContract;

public class WebViewActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initActivity() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        webView = findViewById(R.id.webview);

        WebSettings settings = webView.getSettings();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
//        if (mDensity == 120) {
//            settings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
//        }else if (mDensity == 160) {
//            settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        }else if (mDensity == 240) {
//            settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//
//        }
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.loadUrl("https://www.baidu.com/");

    }

    @Nullable
    @Override
    protected IBaseContract.Presenter createPresenter() {
        return null;
    }
}