package com.example.toutiaonews.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.activity_bean.ChannelBean;
import com.example.toutiaonews.MyAdapter;
import com.example.toutiaonews.R;

import java.util.ArrayList;
import java.util.List;


public class MyFragment extends Fragment {
    private View myView;
    private RecyclerView myRcv;
    private List<ChannelBean> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_my, container, false);
        myRcv=myView.findViewById(R.id.my_rcv);
        String[] titles = getResources().getStringArray(R.array.channel);
        data=new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            data.add(new ChannelBean(titles[i],true));
        }
        myRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRcv.setAdapter(new MyAdapter(data));

        return myView;
    }

}
