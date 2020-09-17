package com.example.toutiaonews.welcome.contract;

import com.example.framework2.base.BasePresenter;
import com.example.framework2.base.IView;
import com.example.common.mode.HomeRecommendBean;

public class RecommendContract {

    public interface RecommendView extends IView{
        void onRecommendData(HomeRecommendBean homeRecommendBean);
    }

    public static abstract class RecommendPresenter extends BasePresenter<RecommendView>{
        public abstract void getRecommendData(String category);
    }

}
