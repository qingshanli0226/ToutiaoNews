package com.bw.homemodule;

import android.widget.TextView;

import com.example.farmework.base.BaseFragment;

public class DebugFragment extends BaseFragment {
    private String str;

    public DebugFragment(String str) {
        this.str = str;
    }

    @Override
    protected int bandLayout() {
        return R.layout.fragment_debug;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        TextView textView=findViewById(R.id.show);
        textView.setText(str+"");
    }
}
