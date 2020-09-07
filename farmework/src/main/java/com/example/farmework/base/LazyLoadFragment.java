package com.example.farmework.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LazyLoadFragment extends Fragment {
    private static final String TAG = LazyLoadFragment.class.getSimpleName();

    private boolean isFirstEnter = true; //是否第一次进入;
    private boolean isReuseView = true; //是否复用，默认复用
    private boolean isFragmentVisible;

    private View rootView;
    //setUserVisibleHint()在Fragment创建时会先被调用一次
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if(rootView == null){
            //如果为进行初始化，不进行处理
            return;
        }
        //如果是第一次进入并且可见
        if(isFirstEnter && isVisibleToUser){
            onFragmentFirstVisible();//回调当前fragment首次可见
            isFirstEnter = false;//第一次进入的标识改为false
        }
        //如果不是第一次进入，可见的时候
        if(isVisibleToUser){
            isFragmentVisible = true;
            onFragmentVisibleChange(isFragmentVisible);
            return;
        }
        //如果当前fragment不可见且true
        if(isFragmentVisible){
            isFragmentVisible = false;
            onFragmentVisibleChange(isFragmentVisible);//回调fragment不可见
        }
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     *
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected void onFragmentFirstVisible() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //保证回调发生在创建完成之后，以便支持ui操作
        if(rootView == null){
            rootView = view;
            if(getUserVisibleHint()){
                onFragmentFirstVisible();
                isFirstEnter = false;
            }
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
        super.onViewCreated(view, savedInstanceState);
    }
    //设置view复用，默认开启
    protected void reuseView(boolean isReuse){
        isReuseView = isReuse;
    }

    protected boolean isFragmentVisible(){
        return isFragmentVisible;
    }
    /**重置变量*/
    private void resetVariavle(){
        isFirstEnter = true;
        isReuseView = true;
        isFragmentVisible = false;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        resetVariavle();
    }
}
