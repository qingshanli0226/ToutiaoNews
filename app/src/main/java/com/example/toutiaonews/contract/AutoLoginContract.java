package com.example.toutiaonews.contract;

import com.example.farmework.base.BasePresenter;
import com.example.farmework.base.IView;
import com.example.toutiaonews.bean.AutoLoginEntity;


public class AutoLoginContract {
    public interface IAutoLoginView extends IView {
        void onAutoLoginData(AutoLoginEntity autoLoginEntity);
    }

    public static abstract class AutoLoginPresenter extends BasePresenter<IAutoLoginView> {
        public abstract void getAutoLoginData(String token);
    }
}
