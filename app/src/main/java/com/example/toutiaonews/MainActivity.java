package com.example.toutiaonews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chaychan.library.BottomBarLayout;

public class MainActivity extends AppCompatActivity {
    private BottomBarLayout bbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    private void initData() {

    }

    private void initView() {
        bbl = findViewById(R.id.bbl);

    }
}
