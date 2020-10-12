package com.example.farmework.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.common.cache.CacheManager;

public abstract class BaseFragment extends Fragment {
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.rootView != null) {
            return rootView;
        }
        View lRootView = inflater.inflate(bandLayout(), container, false);
        this.rootView = lRootView;
        return lRootView;
    }
    protected abstract int bandLayout();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }


    protected abstract void initData();

    protected abstract void initView();

    public <T extends View> T findViewById(@IdRes int id) {
        return rootView.findViewById(id);
    }

    protected void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showError(String code, String message) {
        Toast.makeText(getActivity(), "code:"+code+"  message:"+message, Toast.LENGTH_SHORT).show();
    }
    protected void launchActivity(Class activity,Bundle bundle){
        Intent intent = new Intent();
        if(bundle == null){
            bundle = new Bundle();
        }
        intent.putExtras(bundle);
        intent.setClass(getContext(), activity);
        startActivity(intent);
    }

}