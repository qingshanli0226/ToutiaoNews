package com.example.videolibrary;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.framework.bean.BaseMVPFragment;
import com.example.net.activity_bean.VideoBean;
import com.example.videolibrary.adapter.VideoAdapter;
import com.example.videolibrary.mvp.VideoChildContract;
import com.example.videolibrary.mvp.VideoChildPresenterImpl;
import com.example.videolibrary.utils.SqlUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;


public class VideoChildFragment extends BaseMVPFragment<VideoChildPresenterImpl, VideoChildContract.IVideoChildView> implements VideoChildContract.IVideoChildView, OnRefreshLoadMoreListener {
    private static final String TAG = "Video";
    private RecyclerView fragmentVideoChildRv;
    private SmartRefreshLayout fragmentVideoChildSmart;
    private VideoAdapter videoAdapter;
    private List<VideoDataBean> videoDataBeans;
    private String category;
    private boolean isRefresh = true;
    private SQLiteDatabase db;

//    private long time;

    public VideoChildFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_child;
    }

    @Override
    protected void initData() {
        videoDataBeans = new ArrayList<>();
        videoAdapter = new VideoAdapter(videoDataBeans);
        fragmentVideoChildRv.setAdapter(videoAdapter);
        fragmentVideoChildRv.setLayoutManager(new LinearLayoutManager(getContext()));

        fragmentVideoChildSmart.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initView() {
        category = getArguments() != null ? getArguments().getString("category", "") : null;
        fragmentVideoChildRv = findViewById(R.id.fragment_video_child_rv);
        fragmentVideoChildSmart = findViewById(R.id.fragment_video_child_smart);
    }

    @Override
    protected void initHttpData() {
        Log.i(TAG, "initHttpData:     category       " + category);
        ihttpPresenter.getVideoChildData(category);
    }

    @Override
    protected void initPresenter() {
        ihttpPresenter = new VideoChildPresenterImpl();
    }

    @Override
    public void onVideoChildData(VideoBean videoBean) {
        Gson gson = new Gson();
        String toJson = gson.toJson(videoBean);
        List<VideoBean.DataBean> data = videoBean.getData();
        db = new SqlUtils(getContext(), "videoData.db", null, 2).getWritableDatabase();
        if (isRefresh) {
            videoDataBeans.clear();
        }
        //如果没有数据 通知用户 并且不执行以下代码
        if (data.size() == 0) {

            if (!isRefresh) {
                return;
            }
            Toast.makeText(getContext(), "您刷新的频率太快了,暂无数据!!!", Toast.LENGTH_SHORT).show();
            //数据库获取数据
            int videoDataNum = db.query("videoData", null, null, null, null, null, null).getCount();
            if (videoDataNum > 0) {
                Cursor cursor = db.query("videoData", null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    String voideDataStr = cursor.getString(cursor.getColumnIndex("voideDataStr"));
                    Log.i("yueaa", "onVideoChildData: " + voideDataStr);

                    VideoDataBean videoDataBean = gson.fromJson(voideDataStr, VideoDataBean.class);
                    String articleUrl = videoDataBean.getArticle_url();
                    Log.i(TAG, "onVideoChildData:         " + articleUrl);
                    videoDataBeans.add(videoDataBean);
                    videoAdapter.notifyDataSetChanged();

                }
            } else {
                Log.i(TAG, "onVideoChildData AAA:  数据库无数据 ");
            }
            return;
        }

        if (videoDataBeans.size() > 0) {
            long videoDataTime = SPUtils.getInstance().getLong("videoDataTime");
            if (((System.currentTimeMillis() / 1000) - videoDataTime) < 30) {
                Log.i(TAG, "onVideoChildData:   小于30秒  不请求数据 ");
                return;
            }
        }


        //获取到数据 储存当前时间
        SPUtils.getInstance().put("videoDataTime", System.currentTimeMillis() / 1000);
        int videoDatanum = db.query("videoData", null, null, null, null, null, null).getCount();
        if (videoDatanum >= 10) {
            db.delete("videoData", null, null);
        }

        for (int i = 0; i < data.size(); i++) {
            String voideDataStr = data.get(i).getContent();
            Log.i("yueyue", "onVideoChildData:  content " + voideDataStr);
            VideoDataBean videoDataBean = gson.fromJson(voideDataStr, VideoDataBean.class);
            String articleUrl = videoDataBean.getArticle_url();
            Log.i(TAG, "onVideoChildData:         " + articleUrl);
            videoDataBeans.add(videoDataBean);
            videoAdapter.notifyDataSetChanged();

            //存储到数据库
            if (videoDatanum < 10) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("voideDataStr", voideDataStr);
//            contentValues.put("UserInfoBean", videoDataBean.getUser_info().toString());
//            contentValues.put("ShareLargeImageBean", videoDataBean.getShare_large_image().toString());
                db.insert("videoData", null, contentValues);
            }
        }
        Log.i(TAG, "initHttpData:     videoDataBeans.size()       " + videoDataBeans.size());
    }

    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoaing() {

    }

    @Override
    public void hideLoaing() {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        fragmentVideoChildSmart.finishLoadMore();
        isRefresh = false; //说明是加载
        ihttpPresenter.getVideoChildData(category);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        fragmentVideoChildSmart.finishRefresh();
        isRefresh = true;//说明是刷新 需要清除list的数据
        ihttpPresenter.getVideoChildData(category);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        db.delete("videoData", null, null);
    }
}
