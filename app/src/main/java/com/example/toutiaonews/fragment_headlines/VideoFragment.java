package com.example.toutiaonews.fragment_headlines;

import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.framework2.mvp.view.BaseLJZFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.fragment_headlines.adapter.VideoAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseLJZFragment {
    private SmartRefreshLayout mRefreshListSrl;
    private RecyclerView mVideoListRv;
    private List<String> list;
    private VideoAdapter videoAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        mRefreshListSrl = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_list_srl);
        mVideoListRv = (RecyclerView) rootView.findViewById(R.id.video_list_rv);
        mVideoListRv.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        list = new ArrayList<>();
        videoAdapter = new VideoAdapter(R.layout.item_video, list);
        mVideoListRv.setAdapter(videoAdapter);
    }

    @Override
    protected void lazyLoad() {
        list.add("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319104618910544.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4");

        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }
}
