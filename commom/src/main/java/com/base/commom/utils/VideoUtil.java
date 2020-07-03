package com.base.commom.utils;

import android.app.Activity;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.qiniu.pili.droid.shortvideo.PLShortVideoTranscoder;
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener;

import java.io.File;

import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_LOW_MEMORY;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_NO_VIDEO_TRACK;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_SRC_DST_SAME_FILE_PATH;


public class VideoUtil {
    private static PLShortVideoTranscoder mShortVideoTranscoder;
    /**
     * 压缩视频
     *
     * @param mContext
     * @param filepath
     */
    public static void compressVideoResouce(final Activity mContext, String filepath, final
    OnCompressVideoLisenter lisenter) {
        if (TextUtils.isEmpty(filepath)) {
            show(mContext, "请先选择转码文件！");
            return;
        }

        String f = filepath.substring(filepath.lastIndexOf(".") + 1);
        if (TextUtils.isEmpty(f)) {
            show(mContext, "请上传正确的文件");
            return;
        }

        String p = getSdCardPath() + "/NodeShop/video/";
        isFoldersExists(p);
        String vp = p + "upload_video" +  System.currentTimeMillis() + "." + f;
//        deleteFile(vp);
        mShortVideoTranscoder=null;
        //PLShortVideoTranscoder初始化，三个参数，第一个context，第二个要压缩文件的路径，第三个视频压缩后输出的路径
        mShortVideoTranscoder = new PLShortVideoTranscoder(mContext,
                filepath, vp);
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(filepath);
        String height = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        // 视频高度
        String width = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); //
        // 视频宽度
//        String rotation = retr.extractMetadata(MediaMetadataRetriever
//                .METADATA_KEY_VIDEO_ROTATION); // 视频旋转方向
        int transcodingBitrateLevel = 6;
//        DialogUtils.show(mContext, "处理中...", new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                cancleTranscoder();
//            }
//        });

        mShortVideoTranscoder
                .transcode(Integer.parseInt(width), Integer.parseInt(height),
                        getEncodingBitrateLevel(transcodingBitrateLevel), false, new PLVideoSaveListener() {
                            @Override
                            public void onSaveVideoSuccess(String s) {
                                if (lisenter != null) {
                                    lisenter.onSuccess(s);
                                }
                            }

                            @Override
                            public void onSaveVideoFailed(final int errorCode) {

                                switch (errorCode) {
                                    case ERROR_NO_VIDEO_TRACK:
                                        if (lisenter != null) {
                                            lisenter.onFail(errorCode, "该文件没有视频信息");
                                        }
                                        break;
                                    case ERROR_SRC_DST_SAME_FILE_PATH:
                                        if (lisenter != null) {
                                            lisenter.onFail(errorCode, "源文件路径和目标路径不能相同");
                                        }
                                        break;
                                    case ERROR_LOW_MEMORY:
                                        if (lisenter != null) {
                                            lisenter.onFail(errorCode, "手机内存不足，无法对该视频进行压缩！");
                                        }
                                        break;
                                    default:
                                        if (lisenter != null) {
                                            lisenter.onFail(errorCode, "视频压缩失败");
                                        }
                                }

                            }

                            @Override
                            public void onSaveVideoCanceled() {
                                if (lisenter != null) {
                                    lisenter.onSaveVideoCanceled();
                                }
                            }

                            @Override
                            public void onProgressUpdate(float percentage) {
                                if (lisenter != null) {
                                    lisenter.onProgressUpdate(percentage);
                                }
                            }
                        });

    }

    /**
     * 设置压缩质量
     *
     * @param position
     * @return
     */
    private static int getEncodingBitrateLevel(int position) {
        return ENCODING_BITRATE_LEVEL_ARRAY[position];
    }

    /**
     * 选的越高文件质量越大，质量越好
     */
    public static final int[] ENCODING_BITRATE_LEVEL_ARRAY = {
            500 * 1000,
            800 * 1000,
            1000 * 1000,
            1200 * 1000,
            1600 * 1000,
            2000 * 1000,
            2500 * 1000,
            4000 * 1000,
            8000 * 1000,
    };


    public interface OnCompressVideoLisenter {
        void onSuccess(String path);

        void onFail(int code, String e);

        void onSaveVideoCanceled();

        void onProgressUpdate(float percentage);
    }
//    OnCompressVideoLisenter lisenter;
//
//    public void setOnCompressVideoLisenter(OnCompressVideoLisenter lisenter){
//        this.lisenter=lisenter;
//    }

    public static void cancleTranscoder(){
        if (mShortVideoTranscoder!=null){
            mShortVideoTranscoder.cancelTranscode();
            mShortVideoTranscoder=null;
        }
    }

    public static void show(Context context, String msg){
        ToastUtil.show(context, msg);
    }


    /**
     * 判断sd卡下多级文件夹是否存在
     *
     * @param strFolder
     * @return
     */
    public static boolean isFoldersExists(String strFolder) {
        File file = new File(strFolder);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return true;
            } else {
                return false;

            }
        }
        return true;
    }


    /**
     * 删除文件
     *
     * @param path
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        } else {
            return true;
        }

    }


    /**
     * 获取SD卡根目录路径
     *
     * @return
     */
    public static String getSdCardPath() {
        boolean exist = isExitsSdcard();
        String sdpath = "";
        if (exist) {
            sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdpath = "暂无SD卡";
        }
        return sdpath;

    }


    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
