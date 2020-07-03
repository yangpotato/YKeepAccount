package com.base.commom.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.base.commom.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class GlideUtil {

    public static void show(Context mContext, Object url, View imageView){
        Glide.with(mContext).load(url).into((ImageView) imageView);
    }

    public static void showWithDefault(Context mContext, Object url, View imageView, Drawable drawable){
        RequestOptions options = new RequestOptions()
                .error(drawable)
                .placeholder(drawable);
        Glide.with(mContext).load(url).apply(options).into((ImageView) imageView);
    }

    public static void showWithDefault(Context mContext, Object url, View imageView, @DrawableRes int resourceId){
        RequestOptions options = new RequestOptions()
                .error(resourceId)
                .placeholder(resourceId);
        Glide.with(mContext).load(url).apply(options).into((ImageView) imageView);
    }

    public static void showWithDefaultCorners(Context mContext, Object url, View imageView, @DrawableRes int resourceId){
        RequestOptions options = new RequestOptions()
                .error(resourceId)
                .placeholder(resourceId)
                .transforms(new RoundedCorners(10));
        Glide.with(mContext).load(url).apply(options).into((ImageView) imageView);
    }

    public static void showWithCrossFade(Context mContext, Object url, View imageView){
        Glide.with(mContext)
                .load(url)
                .transition(withCrossFade())
                .into((ImageView) imageView);
    }

    public static void showFitCenter(Context mContext, Object url, View imageView, int weight, int height){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(weight, height);
//        Glide.with(mContext).load(url).apply(options).into((ImageView) imageView);
        Glide.with(mContext).load(url).apply(options).into((ImageView) imageView);
    }

    public static void showCorners(Context mContext, int radius, Object url, View imageView){
        MultiTransformation multi = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(radius, 0));
        RequestOptions options = new RequestOptions()
                .dontAnimate()
//                .placeholder(R.drawable.shape_placeholder)
                .apply(bitmapTransform(multi));
        Glide.with(mContext).load(url).apply(options).into((ImageView) imageView);
    }


    public static void showCorners(Context mContext, Object url, ImageView imageView){
        MultiTransformation multi = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(DensityUtils.dip2px(10), 0));
        RequestOptions options = new RequestOptions()
                .dontAnimate()
//                .placeholder(R.drawable.shape_placeholder)
                .apply(bitmapTransform(multi));
        Glide.with(mContext).load(url).apply(options).into(imageView);
    }

    public static void showCorners(Context mContext, Object url, View imageView){
        MultiTransformation multi = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(DensityUtils.dip2px(10), 0));
        RequestOptions options = new RequestOptions()
                .dontAnimate()
//                .placeholder(R.drawable.shape_placeholder)
                .apply(bitmapTransform(multi));
        Glide.with(mContext).load(url).apply(options).into((ImageView) imageView);
    }

    public static void showCircle(Context mContext, Object url, ImageView imageView){
        RequestOptions options = new RequestOptions()
                .dontAnimate()
//                .placeholder(R.drawable.shape_placeholder)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()));
        Glide.with(mContext).load(url).apply(options).into(imageView);
    }

    public static void showCircle(Context mContext, Object url, View imageView){
        RequestOptions options = new RequestOptions()
                .dontAnimate()
//                .placeholder(R.drawable.shape_placeholder)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()));
        Glide.with(mContext).load(url).apply(options).into((ImageView) imageView);
    }

    public static void showSizeOriginal(Context mContext, Object url, View imageView){
        Glide.with(mContext).load(url).apply(new RequestOptions().override(Target.SIZE_ORIGINAL))
                .into((ImageView) imageView);
    }

    public static void showTrendListImg(Context mContext, Object url, View imageView){
        RequestOptions options = new RequestOptions()
                .dontAnimate();
//                .placeholder(R.drawable.shape_placeholder);
//                .placeholder(R.drawable.placeholder);
//                .placeholder(R.mipmap.placeholder);
        Glide.with(mContext).load(url).apply(options).into((ImageView) imageView);
    }

    public static void showBlur(Context mContext, Object url, View view, int reid){
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .placeholder(reid)
                .error(reid)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25)));
        Glide.with(mContext).load(url).apply(options).into((ImageView)view);
    }

    public static void showNoCache(Context mContext, Object url, View view){
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(mContext).load(url).apply(options).into((ImageView)view);
    }

    public static void showVideoImage(Context mContext, String video, View view) {
        Glide.with(mContext).load(video + "?x-oss-process=video/snapshot,t_1000,m_fast,ar_auto").into((ImageView)view);
    }
}
