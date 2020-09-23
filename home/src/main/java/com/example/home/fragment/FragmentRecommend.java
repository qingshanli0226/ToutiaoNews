package com.example.home.fragment;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework2.mvp.view.BaseFragment;
import com.example.framework2.mvp.view.LoadingView;
import com.example.home.R;
import com.example.home.adapter.MyAdapter;
import com.example.home.contract.ContractRecommend;
import com.example.home.model.ModelRecommend;
import com.example.home.presenter.PresenterRecommend;
import com.example.net.bean.ContentBean;
import com.example.net.bean.Recommend;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentRecommend extends BaseFragment<PresenterRecommend> implements ContractRecommend.View, OnRefreshListener {
    private ArrayList<Recommend.DataBean> list_recommend = new ArrayList<>();
    private ArrayList<ContentBean> list_content = new ArrayList<>();
    private LoadingView loadingImage;
    private SmartRefreshLayout refreshListSrlRecommend;
    private RecyclerView videoListRvRecommend;
    private MyAdapter myAdapter;
    private String str = "推荐";
    private PresenterRecommend presenterRecommend;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        presenterRecommend = new PresenterRecommend(new ModelRecommend(), this);

        loadingImage = (LoadingView) findViewById(R.id.loading_image);
        refreshListSrlRecommend = findViewById(R.id.refresh_list_srl_recommend);
        videoListRvRecommend = (RecyclerView) findViewById(R.id.video_list_rv_recommend);
        refreshListSrlRecommend.setOnRefreshListener(this);

        myAdapter = new MyAdapter(getContext(),list_content);
        videoListRvRecommend.setAdapter(myAdapter);
        videoListRvRecommend.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initData() {
        long currentTimeMillis = System.currentTimeMillis();
        presenterRecommend.getRecommendData(currentTimeMillis,str);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.activity_fragment_recommend;
    }

    @Override
    public void getRecommendData(Recommend recommendBean) {
        refreshListSrlRecommend.finishRefresh(true);

        list_content.clear();

        List<Recommend.DataBean> data = recommendBean.getData();
        list_recommend.addAll(data);

        for (int i = 0; i < list_recommend.size(); i++) {
            String content = list_recommend.get(i).getContent();
            ContentBean contentBean = new Gson().fromJson(content, ContentBean.class);
            list_content.add(contentBean);
        }

        Log.d("1008611", "getRecommendData: "+list_content.size());
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        presenterRecommend.getRecommendData(System.currentTimeMillis(),str);
    }
}
