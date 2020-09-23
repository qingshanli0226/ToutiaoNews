package com.example.toutiaonews.fragment_home;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.framework2.mvp.view.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.fragment_home.adapter.MyAdapter;
import com.example.toutiaonews.fragment_home.adapter.MyViewPagerAdapter;
import com.example.home.fragment.FragmentArticle;
import com.example.home.fragment.FragmentAutomobile;
import com.example.home.fragment.FragmentDelicious;
import com.example.home.fragment.FragmentExplore;
import com.example.home.fragment.FragmentFashion;
import com.example.home.fragment.FragmentFinance;
import com.example.home.fragment.FragmentGame;
import com.example.home.fragment.FragmentHealth;
import com.example.home.fragment.FragmentHomeHistory;
import com.example.home.fragment.FragmentHomeVideo;
import com.example.home.fragment.FragmentHotspot;
import com.example.home.fragment.FragmentInternational;
import com.example.home.fragment.FragmentMilitary;
import com.example.home.fragment.FragmentParenting;
import com.example.home.fragment.FragmentRecommend;
import com.example.home.fragment.FragmentRecreation;
import com.example.home.fragment.FragmentScience;
import com.example.home.fragment.FragmentSociety;
import com.example.home.fragment.FragmentSport;
import com.example.home.fragment.FragmentStory;
import com.example.home.fragment.FragmentTravel;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.Objects;

public class FragmentHome extends BaseFragment {
    private EditText etHomeSearch;
    private TabLayout tabMainTopTitle;
    private ImageView ivHomeAddTitle;
    private ViewPager viewpager;
    private ArrayList<String> list_item = new ArrayList<>();
    private ArrayList<Fragment> list_fragments = new ArrayList<>();
    private MyAdapter myAdapter;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        etHomeSearch = (EditText) findViewById(R.id.et_home_search);
        tabMainTopTitle = (TabLayout) findViewById(R.id.tab_main_top_title);
        ivHomeAddTitle = (ImageView) findViewById(R.id.iv_home_add_title);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        String[] stringArray = Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.channel);
        list_item.clear();
        for (int i = 0; i < stringArray.length; i++) {
            list_item.add(stringArray[i]);
            Log.d("ljl", "initView: "+list_item.get(i));
        }
        Log.d("ljl", "initView: "+list_item.size());

        myAdapter = new MyAdapter(getContext(),list_item);

        //初始化viewpager
        getTheViewPager();
        tabMainTopTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        myViewPagerAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager(),list_fragments,list_item);
        viewpager.setAdapter(myViewPagerAdapter);
        tabMainTopTitle.setupWithViewPager(viewpager);

    }

    private void getTheViewPager() {
        list_fragments.add(new FragmentRecommend());
        list_fragments.add(new FragmentHomeVideo());
        list_fragments.add(new FragmentHotspot());
        list_fragments.add(new FragmentSociety ());
        list_fragments.add(new FragmentRecreation());
        list_fragments.add(new FragmentScience());
        list_fragments.add(new FragmentAutomobile());
        list_fragments.add(new FragmentSport());
        list_fragments.add(new FragmentFinance());
        list_fragments.add(new FragmentMilitary());
        list_fragments.add(new FragmentInternational());
        list_fragments.add(new FragmentFashion());
        list_fragments.add(new FragmentGame());
        list_fragments.add(new FragmentTravel());
        list_fragments.add(new FragmentHomeHistory());
        list_fragments.add(new FragmentExplore());
        list_fragments.add(new FragmentDelicious());
        list_fragments.add(new FragmentParenting());
        list_fragments.add(new FragmentHealth());
        list_fragments.add(new FragmentStory());
        list_fragments.add(new FragmentArticle());
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
