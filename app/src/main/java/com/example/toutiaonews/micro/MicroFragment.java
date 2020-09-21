package com.example.toutiaonews.micro;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework2.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.micro.adapter.MicroAdapter;

import java.util.ArrayList;

import cn.jzvd.JzvdStd;

public class MicroFragment extends BaseFragment implements View.OnClickListener {

    public static final int REQUEST_PHOTO = 101;//图库
    public static final int REQUEST_VIDICON = 102;//摄像机

    private LinearLayout llText;//文字
    private LinearLayout llPhoto;//图片
    private LinearLayout llVideo;//视频

    private EditText etText;
    private RecyclerView photoRv;
    private JzvdStd videoJz;
    private MicroAdapter microAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_micro;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        llText = findViewById(R.id.ll_text);
        llPhoto = findViewById(R.id.ll_photo);
        llVideo = findViewById(R.id.ll_video);

        llText.setOnClickListener(this);
        llPhoto.setOnClickListener(this);
        llVideo.setOnClickListener(this);

        etText = findViewById(R.id.et_text);
        photoRv = findViewById(R.id.photo_rv);
        videoJz = findViewById(R.id.video_jz);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_text:
                text();
                break;
            case R.id.ll_photo:
                photo();
                break;
            case R.id.ll_video:
                video();
                break;
        }
    }

    //拍视频
    private void video() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 200);
        }

        //打开摄像机
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, REQUEST_VIDICON);
    }

    //传照片
    private void photo() {
        //打开图库
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PHOTO);
    }

    //编辑文字
    private void text() {
        if (etText.getVisibility() == View.VISIBLE) {
            etText.setVisibility(View.GONE);//隐藏文字
        } else {
            etText.setVisibility(View.VISIBLE);//显示文字
        }
    }

    ArrayList<String> list = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode 请求码  resultCode 结果码
        if (requestCode == REQUEST_VIDICON && resultCode == Activity.RESULT_OK) {
            if (videoJz.getVisibility() == View.GONE) {
                videoJz.setVisibility(View.VISIBLE);//显示视频
                videoJz.mRetryLayout.setVisibility(View.GONE);
                videoJz.batteryLevel.setVisibility(View.GONE);
                videoJz.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);//去掉黑框

                Uri uri = data.getData();//视频地址

                videoJz.setUp(getRealPathFromURI(uri), "我的视频", JzvdStd.SCREEN_WINDOW_NORMAL);
                videoJz.startWindowTiny();
            } else {
                videoJz.setVisibility(View.GONE);
            }
        } else if (requestCode == REQUEST_PHOTO && resultCode == Activity.RESULT_OK) {
            if (photoRv.getVisibility() == View.GONE) {
                photoRv.setVisibility(View.VISIBLE);//显示图片

                Uri data1 = data.getData();//图库选择
                list.add(data1.toString());

                microAdapter = new MicroAdapter(R.layout.item_micro_image, list);
                photoRv.setAdapter(microAdapter);
                photoRv.setLayoutManager(new GridLayoutManager(getContext(), 3));
            } else {
                photoRv.setVisibility(View.GONE);
            }

        }
    }

    //将uri转换成path地址
    private String getRealPathFromURI(Uri uri) {
        String[] proj = {
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor = this.getActivity().managedQuery(uri, proj, null, null, null);
        int audioColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(audioColumnIndex);
        return path;
    }

}
