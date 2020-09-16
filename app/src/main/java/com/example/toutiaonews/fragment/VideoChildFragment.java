package com.example.toutiaonews.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.net.activity_bean.VideoBean;
import com.example.toutiaonews.R;
import com.example.toutiaonews.adapter.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoChildFragment extends Fragment {
    private RecyclerView fragmentVideoChildRv;

    private VideoAdapter videoAdapter;
    private List<VideoBean> videoBeans;

    public VideoChildFragment() {
        // Required empty public constructor
    }

    public VideoChildFragment(List<VideoBean> videoBeans) {
        this.videoBeans = videoBeans;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentVideoChildRv = view.findViewById(R.id.fragment_video_child_rv);
;
        videoAdapter = new VideoAdapter(videoBeans);
        fragmentVideoChildRv.setAdapter(videoAdapter);
        fragmentVideoChildRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        
    }
}
