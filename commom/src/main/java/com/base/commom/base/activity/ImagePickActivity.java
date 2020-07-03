package com.base.commom.base.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.base.commom.R;
import com.base.commom.listener.PermissionListener;
import com.base.commom.mvp.IBaseContract;
import com.base.commom.utils.FileUtils;
import com.base.commom.utils.Glide4Engine;
import com.base.commom.utils.KeyBoardUtil;
import com.base.commom.utils.LogUtil;
import com.base.commom.utils.ToastUtil;
import com.base.commom.widget.PickerImagePopup;
import com.lxj.xpopup.XPopup;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import static com.zhihu.matisse.MimeType.BMP;
import static com.zhihu.matisse.MimeType.JPEG;
import static com.zhihu.matisse.MimeType.PNG;
import static com.zhihu.matisse.MimeType.WEBP;

public abstract class ImagePickActivity<P extends IBaseContract.Presenter> extends BaseActivity<P> implements PermissionListener {
    protected static int REQUEST_CODE_CHOOSE = 1001;
    protected static int REQUEST_CODE_CAMEAR = 1002;
    private int type;
    protected int videoType = -1;  //0图片 1视频
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

    private boolean selectAll = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelectedUriList = Matisse.obtainResult(data);
            mSelectedPathList = Matisse.obtainPathResult(data);
            if (mSelectedPathList != null && mSelectedPathList.size() > 0)
                onSelectSuccess();
        } else if (requestCode == REQUEST_CODE_CAMEAR && resultCode == RESULT_OK) {
            if (mUri != null) {
                if (mSelectedUriList == null)
                    mSelectedUriList = new ArrayList<>();
                else
                    mSelectedUriList.clear();
                mSelectedUriList.add(mUri);

                if (mSelectedPathList == null)
                    mSelectedPathList = new ArrayList<>();
                else
                    mSelectedPathList.clear();
                if (imageFile != null)
                    mSelectedPathList.add(imageFile.getPath());
                onSelectSuccess();
            }
        }

        if (requestCode == 100 && data != null) {
            if (mSelectedPathList == null)
                mSelectedPathList = new ArrayList<>();
            videoType = getIntent().getIntExtra("Type", 0);
            mSelectedPathList.add(data.getStringExtra("ImagePath"));
            onSelectSuccess();
        }
    }

    protected void startCamera() {
        LogUtil.d("==========>  startCamera  selectAll = " + selectAll);
        if (!selectAll) {
            imageFile = new File(Environment.getExternalStorageDirectory() + "/fb/image/IMG_" + System.currentTimeMillis() + ".jpg");
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
                mUri = FileProvider.getUriForFile(this, "com.yiwang.fb.fileprovider", imageFile);
            } else {
                //步骤三：获取文件Uri
                mUri = Uri.fromFile(imageFile);
            }
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            startActivityForResult(intent, REQUEST_CODE_CAMEAR);
        }
    }

    protected void startMatisse() {
        startMatisse(1);
    }

    /**
     * 打开图片选择框架
     */
    protected void startMatisse(int number) {
        Matisse.from(this)
                .choose(MimeType.of(JPEG, PNG, BMP, WEBP))
                .countable(false)
//                .capture(true)
                .showSingleMediaType(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.yiwang.fb.fileprovider"))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .maxSelectable(number)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    protected void startMatisseAll(int number) {

        Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(false)
                .capture(false)
                .showSingleMediaType(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.yiwang.fb.fileprovider"))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .maxSelectable(number)
                .maxSelectablePerMediaType(9, 1)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }


    /**
     * 打开视频选择框架
     */
    protected void startMatisseVideo() {
        type = 1;
        getPermission(Manifest.permission.CAMERA, ImagePickActivity.this);
    }

    private void getVideo() {
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
     *
     * @param photos
     * @param <T>
     */
    protected <T> void startCompress(List<T> photos) {
        LogUtil.i("开始压缩");
        Luban.with(this)
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
                        LogUtil.i("压缩成功");
                        onCompressSuccess(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("图片压缩失败:" + e);
                        showMessage("图片压缩失败");
                    }
                }).launch();
    }

    protected void showPopup() {

        showPopup(1);
    }

    /**
     * 弹出选择弹窗
     */
    protected void showPopup(int number) {
        LogUtil.d("========>  showPopup   number = " + number);
        KeyBoardUtil.hideKeyBoard(this);
        selectAll = false;
        type = 0;
        selectNumber = number;
        if (mPickerImagePopup == null) {
            mPickerImagePopup = new PickerImagePopup(this);
        }
        mPickerImagePopup.setmListener(id -> {
            if (id == R.id.tv_camera) {
                selectType = 0;
                getPermission(Manifest.permission.CAMERA, ImagePickActivity.this);
//                    startCamera();
            } else if (id == R.id.tv_album) {
                selectType = 1;
                getPermission(Manifest.permission.READ_EXTERNAL_STORAGE, ImagePickActivity.this);
//                    startMatisse(selectNumber);
            }
            mPickerImagePopup.dismiss();
        });
        new XPopup.Builder(this)
                .asCustom(mPickerImagePopup)
                .show();

    }

    /**
     * 弹出选择弹窗
     */
    protected void showPopupAll(int number) {
        LogUtil.d("========>  showPopupAll   number = " + number);
        selectAll = true;
        type = 0;
        selectNumber = number;
        if (mPickerImagePopup == null) {
            mPickerImagePopup = new PickerImagePopup(this);
        }
        mPickerImagePopup.setmListener(id -> {
            if (id == R.id.tv_camera) {
                selectType = 0;
                getPermission(Manifest.permission.CAMERA, ImagePickActivity.this);
//                    startCamera();
            } else if (id == R.id.tv_album) {
                selectType = 1;
                getPermission(Manifest.permission.READ_EXTERNAL_STORAGE, ImagePickActivity.this);
//                    startMatisse(selectNumber);
            }
            mPickerImagePopup.dismiss();
        });
        new XPopup.Builder(this)
                .asCustom(mPickerImagePopup)
                .show();

    }

    @Override
    public void accept(String permission) {
        if (type == 0) {
            if (selectType == 0) {
                if (permission.equals(Manifest.permission.CAMERA))
                    getPermission(Manifest.permission.READ_EXTERNAL_STORAGE, ImagePickActivity.this);
                else if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE))
                    getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, ImagePickActivity.this);
                else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if(selectAll)
                        getPermission(Manifest.permission.RECORD_AUDIO, ImagePickActivity.this);
                    else
                        startCamera();
                }
            } else {
                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE))
                    getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, ImagePickActivity.this);
                else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (selectAll)
                        startMatisseAll(selectNumber);
                    else
                        startMatisse(selectNumber);
                }
            }
        } else {
            if (permission.equals(Manifest.permission.CAMERA))
                getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, ImagePickActivity.this);
            else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                getPermission(Manifest.permission.READ_EXTERNAL_STORAGE, ImagePickActivity.this);
            else if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE))
                getVideo();
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
     *
     * @param file
     */
    protected abstract void onCompressSuccess(File file);

    /**
     * 获取存储路径
     *
     * @return
     */
    public String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/NodeShop/image/";
        File file = new File(path);
        if (!file.exists()) {
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
