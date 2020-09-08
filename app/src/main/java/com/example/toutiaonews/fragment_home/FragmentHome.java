package com.example.toutiaonews.fragment_home;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.framework2.mvp.view.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.adapter.MyAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentHome extends BaseFragment {
    private EditText etHomeSearch;
    private RecyclerView tvId;
    private ImageView ivHomeAddTitle;
    private ViewPager viewpager;
    private PagerTabStrip pagerTabStrip;
    private ArrayList<String> list_item = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        etHomeSearch = (EditText) findViewById(R.id.et_home_search);
        tvId = (RecyclerView) findViewById(R.id.tv_id);
        ivHomeAddTitle = (ImageView) findViewById(R.id.iv_home_add_title);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);

        String[] stringArray = getActivity().getResources().getStringArray(R.array.channel);
        list_item.clear();
        for (int i = 0; i < stringArray.length; i++) {
            list_item.add(stringArray[i]);
            Log.d("ljl", "initView: "+list_item.get(i));
        }
        Log.d("ljl", "initView: "+list_item.size());

        myAdapter = new MyAdapter(getContext(),list_item);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tvId.setLayoutManager(linearLayoutManager);
        tvId.setAdapter(myAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.fragment_home;
    }
}
