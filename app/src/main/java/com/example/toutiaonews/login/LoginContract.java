package com.example.toutiaonews.login;

import com.example.framework.bean.BasePresenter;
import com.example.framework.bean.IView;
import com.example.net.activity_bean.LoginBean;

public class LoginContract {
    public interface ILoginView extends IView {
        void onLogin(LoginBean loginBean);
    }

    public static abstract class LoginPresenter extends BasePresenter<ILoginView> {
        public abstract void login(String name, String password);
    }
}
