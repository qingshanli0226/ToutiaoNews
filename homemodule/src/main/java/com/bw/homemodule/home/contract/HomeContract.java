package com.bw.homemodule.home.contract;

import com.example.common.response.NewsResponse;
import com.example.farmework.base.BasePresenter;
import com.example.farmework.base.IView;

public class HomeContract {

    public interface IHomeView extends IView {
        void onHomeData(NewsResponse newsResponse);
    }

    public static abstract class HomePresenter extends BasePresenter<IHomeView> {
        public abstract void getHomeData(String category);
    }
}
