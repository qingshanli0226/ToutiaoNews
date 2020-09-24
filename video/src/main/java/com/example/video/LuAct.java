package com.example.video;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.framework2.mvp.view.BaseActivity;
import com.qiniu.pili.droid.shortvideo.PLAudioEncodeSetting;
import com.qiniu.pili.droid.shortvideo.PLCameraSetting;
import com.qiniu.pili.droid.shortvideo.PLFaceBeautySetting;
import com.qiniu.pili.droid.shortvideo.PLMicrophoneSetting;
import com.qiniu.pili.droid.shortvideo.PLRecordSetting;
import com.qiniu.pili.droid.shortvideo.PLRecordStateListener;
import com.qiniu.pili.droid.shortvideo.PLShortVideoRecorder;
import com.qiniu.pili.droid.shortvideo.PLVideoEncodeSetting;

import static com.example.common.ARouterCommon.VIDEO_LU_ACT;

@Route(path = VIDEO_LU_ACT)
public class LuAct extends BaseActivity implements PLRecordStateListener, View.OnClickListener {
    private PLShortVideoRecorder mShortVideoRecorder;
    private boolean flag = false;
    @SuppressLint("SdCardPath")
    private String path = "/sdcard";
    @SuppressLint("SdCardPath")
    private String filePath = path + "/record.mp4";

    // 录制选项
    private PLRecordSetting getPlRecordSetting() {
        PLRecordSetting recordSetting = new PLRecordSetting();
        recordSetting.setMaxRecordDuration(10 * 1000); // 10s
        recordSetting.setVideoCacheDir(path);
        recordSetting.setVideoFilepath(filePath);
        return recordSetting;
    }

    // 音频编码选项
    private PLAudioEncodeSetting getPlAudioEncodeSetting() {
        PLAudioEncodeSetting audioEncodeSetting = new PLAudioEncodeSetting();
        audioEncodeSetting.setHWCodecEnabled(true); // true:硬编 false:软编
        return audioEncodeSetting;
    }

    // 视频编码选项
    private PLVideoEncodeSetting getPlVideoEncodeSetting() {
        PLVideoEncodeSetting videoEncodeSetting = new PLVideoEncodeSetting(this);
        videoEncodeSetting.setEncodingSizeLevel(PLVideoEncodeSetting.VIDEO_ENCODING_SIZE_LEVEL.VIDEO_ENCODING_SIZE_LEVEL_480P_1); // 480x480
        videoEncodeSetting.setEncodingBitrate(1000 * 1024); // 1000kbps
        videoEncodeSetting.setEncodingFps(25);
        videoEncodeSetting.setHWCodecEnabled(true); // true:硬编 false:软编
        return videoEncodeSetting;
    }

    // 摄像头采集选项
    private PLCameraSetting getPlCameraSetting() {
        PLCameraSetting cameraSetting = new PLCameraSetting();
        cameraSetting.setCameraId(PLCameraSetting.CAMERA_FACING_ID.CAMERA_FACING_FRONT);
        cameraSetting.setCameraPreviewSizeRatio(PLCameraSetting.CAMERA_PREVIEW_SIZE_RATIO.RATIO_4_3);
        cameraSetting.setCameraPreviewSizeLevel(PLCameraSetting.CAMERA_PREVIEW_SIZE_LEVEL.PREVIEW_SIZE_LEVEL_480P);
        return cameraSetting;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mShortVideoRecorder != null)
            mShortVideoRecorder.resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mShortVideoRecorder != null)
            mShortVideoRecorder.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShortVideoRecorder != null)
            mShortVideoRecorder.destroy();
    }

    @Override
    public void onReady() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onDurationTooShort() {

    }

    @Override
    public void onRecordStarted() {

    }

    @Override
    public void onSectionRecording(long l, long l1, int i) {

    }

    @Override
    public void onRecordStopped() {

    }

    @Override
    public void onSectionIncreased(long l, long l1, int i) {

    }

    @Override
    public void onSectionDecreased(long l, long l1, int i) {

    }

    @Override
    public void onRecordCompleted() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.start_lu) {
            ImageView imageView = findViewById(R.id.start_lu);
            if (flag) {
                mShortVideoRecorder.pause();
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.i7));
                flag = false;
            } else {
                mShortVideoRecorder.beginSection();
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.pause_normal));
                findViewById(R.id.stop_lu).setVisibility(View.VISIBLE);
                flag = true;
            }
        }
        if (id == R.id.stop_lu) {
            findViewById(R.id.stop_lu).setVisibility(View.GONE);
            mShortVideoRecorder.endSection();
        }
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    "android.permission.FLASHLIGHT"
            }, 1000);
        }

        GLSurfaceView mGlSurfaceView = findViewById(R.id.glSurfaceView);

        findViewById(R.id.start_lu).setOnClickListener(this);
        findViewById(R.id.stop_lu).setOnClickListener(this);
        mShortVideoRecorder = new PLShortVideoRecorder();
        mShortVideoRecorder.setRecordStateListener(this);
// 摄像头采集选项
        PLCameraSetting cameraSetting = getPlCameraSetting();
// 麦克风采集选项
        PLMicrophoneSetting microphoneSetting = new PLMicrophoneSetting();
// 视频编码选项
        PLVideoEncodeSetting videoEncodeSetting = getPlVideoEncodeSetting();
// 音频编码选项
        PLAudioEncodeSetting audioEncodeSetting = getPlAudioEncodeSetting();
// 美颜选项
        PLFaceBeautySetting faceBeautySetting = new PLFaceBeautySetting(1.0f, 0.5f, 0.5f);
// 录制选项
        PLRecordSetting recordSetting = getPlRecordSetting();
// 设置录制速度 (默认为 1.0)
        mShortVideoRecorder.prepare(mGlSurfaceView, cameraSetting, microphoneSetting, videoEncodeSetting, audioEncodeSetting, faceBeautySetting, recordSetting);

        mShortVideoRecorder.setRecordSpeed(1.0);
    }

    @Override
    public void initData() {

    }


    @Override
    public int bandLayout() {
        return R.layout.lu_video;
    }
}
