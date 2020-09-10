package com.example.toutiaonews.video.fragment;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework2.base.BaseFragment;
import com.example.toutiaonews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyVideoFragment extends BaseFragment {

    private List<String> videoUrl = new ArrayList<>();
    private RecyclerView videoRv;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_video;
    }

    @Override
    protected void initData() {
        //视频地址
        videoUrl.add("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4");
        videoUrl.add("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319104618910544.mp4");
        videoUrl.add("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4");
        videoUrl.add("http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4");
        videoUrl.add("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4");
        videoUrl.add("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4");
        videoUrl.add("http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4");
        videoUrl.add("http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4");
        videoUrl.add("http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4");
        videoUrl.add("http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4");
    }

    @Override
    protected void initView() {
        videoRv = findViewById(R.id.video_rv);

    }

    //重新创建一个fragment
    public static MyVideoFragment newInstance() {
        return new MyVideoFragment();
    }

}
