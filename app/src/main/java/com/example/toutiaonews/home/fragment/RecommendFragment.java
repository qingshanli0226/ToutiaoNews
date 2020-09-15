package com.example.toutiaonews.home.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.manager.CacheManager;
import com.example.common.mode.ContentBean;
import com.example.common.mode.RecommendBean;
import com.example.framework2.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.adapter.RecommendAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RecommendFragment extends BaseFragment {

    private RecyclerView homeRecommendRv;
    //数据集合
    ArrayList<RecommendBean.DataBean> dataBeans;
    //数据集合
    ArrayList<ContentBean> contentBeans = new ArrayList<>();
    //适配器
    RecommendAdapter recommendAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initData() {
        //获取recommendBean数据
        RecommendBean recommendBean = CacheManager.getCacheManager().getRecommendBean();
        //添加数据
        dataBeans = (ArrayList<RecommendBean.DataBean>) recommendBean.getData();
        Gson gson = new Gson();
        for (int i = 0; i < dataBeans.size() ; i++) {
            //把json数据转换为contentBean对象
            ContentBean contentBean = gson.fromJson(dataBeans.get(i).getContent(), ContentBean.class);
            contentBeans.add(contentBean);
        }
        //创建适配器
        recommendAdapter = new RecommendAdapter(R.layout.item_home_recommend,contentBeans);
        homeRecommendRv.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRecommendRv.setAdapter(recommendAdapter);
    }

    @Override
    protected void initView() {
        homeRecommendRv = (RecyclerView) findViewById(R.id.homeRecommendRv);
    }
}
