package com.example.toutiaonews.fragment.micro;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toutiaonews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MicroFragment extends Fragment {


    public MicroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_micro, container, false);
    }

}