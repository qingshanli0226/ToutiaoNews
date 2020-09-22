package com.example.toutiaonews.contract;

import com.example.farmework.base.BasePresenter;
import com.example.farmework.base.IView;
import com.example.toutiaonews.bean.LoginEntity;

public class LoginContract  {
    public interface ILoginView extends IView {
        void onLoginData(LoginEntity loginEntity);
    }

    public static abstract class ILoginPresenter extends BasePresenter<LoginContract.ILoginView> {
        public abstract void getLoginData(String name,String password);
    }
}
