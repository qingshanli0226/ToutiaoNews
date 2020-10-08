package com.example.toutiaonews.register;

import com.example.farmework.base.BasePresenter;
import com.example.farmework.base.IView;
import com.example.toutiaonews.bean.RegisterEntity;

public class RegisterContract {
    public interface registerView extends IView {
        void onRegisterData(RegisterEntity registerEntity);
    }

    public static abstract class registerPresenter extends BasePresenter<RegisterContract.registerView> {
        public abstract void getRegisterData(String name,String password);
    }
}
