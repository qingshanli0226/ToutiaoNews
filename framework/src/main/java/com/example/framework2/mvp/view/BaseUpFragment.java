package com.example.framework2.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.framework.R;
<<<<<<< HEAD
=======

>>>>>>> 75d528a3727f427aeb5545d1335e6d640cf6ae97
import com.example.framework2.mvp.presenter.IPresenter;


public abstract class BaseUpFragment<P extends IPresenter> extends Fragment implements IFragment, IView, View.OnClickListener {
    protected P mPresenter;
    private View baseView;
    protected ProgressBar mUpPb;

    @Override
    public <V extends View> V findViewById(int id) {
        return baseView.findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        baseView = inflater.inflate(bandLayout(), container, false);
        mUpPb = (ProgressBar)baseView. findViewById(R.id._up_pb);
        return baseView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initPresenter();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }


    @Override
    public void showView() {

    }

    @Override
    public void hideView() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
