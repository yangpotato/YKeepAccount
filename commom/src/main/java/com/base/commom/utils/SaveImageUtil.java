package com.base.commom.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SaveImageUtil {


    public static Bitmap createBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        // 初始化绘制纹理图
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Bitmap targetBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 初始化目标画布
        Canvas targetCanvas = new Canvas(targetBitmap);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        // 利用画笔将纹理图绘制到画布上面
        targetCanvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), DensityUtils.dip2px(6), DensityUtils.dip2px(6), paint);
//        targetCanvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), 0, 0, paint);
        bitmap.recycle();
        return targetBitmap;
//        return bitmap;
    }


    public static void saveBitmap(final Context mContext, final Bitmap bitmap) {
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fos;
                try {
                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_PICTURES;
//            File root = Environment.getExternalStorageDirectory();
                    File file = new File(dirPath, System.currentTimeMillis() + "." + "png");
                    fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.flush();
                    fos.close();
//            Toast.makeText(mContext, "已保存到相册！", Toast.LENGTH_SHORT).show();
                    MediaScannerConnection.scanFile(mContext, new String[]{file.getAbsolutePath()},
                            new String[]{"image/png"}, new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(final String path, Uri uri) {
                                    mainHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
//                                            ToastUtil.showMessage(mContext, "已保存到相册");
                                            ToastUtil.show("已保存到相册");
//                                            Toast.makeText(mContext, "已保存到相册", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
//                    CommonUtils.showMessage(NongBaBiApp.getInstance().getApplicationContext(), "已保存到相册");
                } catch (Exception e) {
                    e.printStackTrace();
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
//                            ToastUtil.showMessage(mContext, "保存失败，请检查是否缺少储存权限");
                            ToastUtil.show("保存失败，请检查是否缺少储存权限");
//                            Toast.makeText(mContext, "保存失败，请检查是否缺少储存权限", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
}


