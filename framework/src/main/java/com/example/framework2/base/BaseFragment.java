package com.example.framework2.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.framework2.R;
import com.github.nukc.stateview.StateView;

public abstract class BaseFragment extends LazyLoadFragment {

    private View rootView;//根view
    protected Activity mActivity;
    private StateView mStateView;//用于显示加载中、网络异常，空布局、内容布局;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);

            mStateView = StateView.inject(getStateViewRoot());
            if (mStateView != null) {
                mStateView.setRetryResource(R.layout.page_net_error);//错误页
            }
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    public View getStateViewRoot() {
        return rootView;
    }

    protected abstract int getLayoutId();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }


    protected abstract void initData();

    protected abstract void initView();

    //注解。表示一个资源id，不能随便传递一个整型
    public <T extends View> T findViewById(@IdRes int id) {
        return rootView.findViewById(id);
    }

    //打印log
    public void printLog(String message) {
        Log.d("a", "FragmentLog---->" + message);
    }

    //短吐司
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    //Activity跳转
    public void launchActivity(Class launchActivityClass, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        Intent intent = new Intent(getActivity(), launchActivityClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rootView != null) {
            rootView = null;
        }
    }

}
