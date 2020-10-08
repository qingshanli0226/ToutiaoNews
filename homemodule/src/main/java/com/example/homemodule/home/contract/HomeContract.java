package com.example.homemodule.home.contract;

import com.example.common.entity.News;
import com.example.farmework.base.BasePresenter;
import com.example.farmework.base.IView;

import java.util.ArrayList;

public class HomeContract {

    public interface IHomeView extends IView {
        void onHomeData(ArrayList<News> newsList);
    }

    public static abstract class HomePresenter extends BasePresenter<IHomeView> {
        public abstract void getHomeData(String category,long lastTime);
    }
}
