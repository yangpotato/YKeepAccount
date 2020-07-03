package com.base.commom.base.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import com.base.commom.R;
import com.base.commom.listener.PermissionListener;
import com.base.commom.mvp.IBaseContract;
import com.base.commom.utils.Glide4Engine;
import com.base.commom.utils.LogUtil;
import com.base.commom.widget.PickerImagePopup;
import com.lxj.xpopup.XPopup;
import com.yanzhenjie.permission.AndPermission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;
import static com.zhihu.matisse.MimeType.BMP;
import static com.zhihu.matisse.MimeType.JPEG;
import static com.zhihu.matisse.MimeType.PNG;
import static com.zhihu.matisse.MimeType.WEBP;

public abstract class ImagePickFragment<P extends IBaseContract.Presenter> extends BaseFragment<P> implements  PermissionListener {
    protected static int REQUEST_CODE_CHOOSE = 1001;
    protected static int REQUEST_CODE_CAMEAR = 1002;
    /**
     * 选择图片得到的Uri列表
     */
    protected List<Uri> mSelectedUriList;
    /**
     * 选择图片得到的路径列表
     */
    protected List<String> mSelectedPathList;
    /**
     * 拍照获得的Uri
     */
    protected Uri mUri;

    private File imageFile;

    private PickerImagePopup mPickerImagePopup;

    // 0:相机  1：相册
    private int selectType = 0;

    private int selectNumber = 1;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelectedUriList = Matisse.obtainResult(data);
            mSelectedPathList = Matisse.obtainPathResult(data);
            if(mSelectedPathList != null && mSelectedPathList.size() > 0)
                onSelectSuccess();
        }else if(requestCode == REQUEST_CODE_CAMEAR && resultCode == RESULT_OK){
            if(mUri != null) {
                if(mSelectedUriList == null)
                    mSelectedUriList = new ArrayList<>();
                else
                    mSelectedUriList.clear();
                mSelectedUriList.add(mUri);

                if(mSelectedPathList == null)
                    mSelectedPathList = new ArrayList<>();
                else
                    mSelectedPathList.clear();
                if(imageFile != null)
                    mSelectedPathList.add(imageFile.getPath());
                onSelectSuccess();
            }
        }
    }

    protected void startCamera(){
        imageFile = new File(Environment.getExternalStorageDirectory() + "/NodeShop/image/IMG_" + System.currentTimeMillis() + ".jpg");
        if (!imageFile.exists()) {
            if (!imageFile.getParentFile().exists()) {
                imageFile.getParentFile().mkdirs();
            }
            try {
                imageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //步骤二：Android 7.0及以上获取文件 Uri
            mUri = FileProvider.getUriForFile(getActivity(), "com.yiwang.fb.fileprovider", imageFile);
        } else {
            //步骤三：获取文件Uri
            mUri = Uri.fromFile(imageFile);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, REQUEST_CODE_CAMEAR);
    }

    protected void startMatisse(){
        startMatisse(1);
    }

    /**
     * 打开图片选择框架
     */
    protected void startMatisse(int number){
        Matisse.from(this)
                .choose(MimeType.of(JPEG, PNG, BMP, WEBP))
                .countable(false)
                .capture(true)
                .showSingleMediaType(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.yiwang.fb.fileprovider"))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .maxSelectable(number)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }


    /**
     * 打开视频选择框架
     */
    protected void startMatisseVideo(){
        Matisse.from(this)
                .choose(MimeType.ofVideo())
                .countable(false)
                .capture(true)
                .showSingleMediaType(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.yiwang.fb.fileprovider"))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    /**
     * 开启压缩
     * 尽量使用mSelectedPathList  不要使用mSelectedUriList
     * @param photos
     * @param <T>
     */
    protected <T> void startCompress(List<T> photos){
        Luban.with(getActivity())
                .load(photos)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LogUtil.d("开始压缩");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtil.d("压缩成功");
                        onCompressSuccess(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("压缩失败");
                        Toast.makeText(curActivity, "图片压缩失败", Toast.LENGTH_SHORT).show();
                    }
                }).launch();
    }
    protected void showPopup(){
        showPopup(1);
    }

    /**
     * 弹出选择弹窗
     */
    protected void showPopup(int number){
        selectNumber = number;
        if(mPickerImagePopup == null){
            mPickerImagePopup = new PickerImagePopup(getActivity());
        }
        mPickerImagePopup.setmListener(new PickerImagePopup.OnPickerVImageClickListener() {
            @Override
            public void onClick(int id) {
                if(id == R.id.tv_camera){
//                    startCamera();
                    selectType = 0;
                    getPermission(Manifest.permission.CAMERA, ImagePickFragment.this);
                }else if(id == R.id.tv_album){
//                    startMatisse(number);
                    selectType = 1;
                    getPermission(Manifest.permission.READ_EXTERNAL_STORAGE, ImagePickFragment.this);
                }
                mPickerImagePopup.dismiss();
            }
        });
        new XPopup.Builder(getActivity())
                .asCustom(mPickerImagePopup)
                .show();
    }

    protected void getPermission(String permission, PermissionListener permissionListener) {
        AndPermission.with(this)
                .runtime()
                .permission(permission)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    permissionListener.accept(permission);
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    permissionListener.refuse(permission);
                })
                .start();
    }

    @Override
    public void accept(String permission) {
        if(selectType == 0) {
            if (permission.equals(Manifest.permission.CAMERA))
                getPermission(Manifest.permission.READ_EXTERNAL_STORAGE, this);
            else if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE))
                getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, this);
            else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                startCamera();
        }else {
            if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE))
                getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, this);
            else if(permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                startMatisse(selectNumber);
        }
    }

    @Override
    public void refuse(String permission) {

    }

    /**
     * 图片选择成功回调
     */
    protected abstract void onSelectSuccess();

    /**
     * 图片成功压缩回调
     * @param file
     */
    protected abstract void onCompressSuccess(File file);

    /**
     * 获取存储路径
     * @return
     */
    public String getPath(){
        String path = Environment.getExternalStorageDirectory() + "/NodeShop/image/";
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
//        if (!file.exists()) {
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (!file.exists()){
//            file.mkdirs();
//        }
//        if (file.mkdirs()) {
//            return path;
//        }
        return path;
    }

}
