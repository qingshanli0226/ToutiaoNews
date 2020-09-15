package com.example.user.contract;

import com.example.framework2.base.BasePresenter;
import com.example.framework2.base.IView;
import com.example.common.mode.LoginBean;
import com.example.common.mode.RegisterBean;

public class LoginRegisterContract {

    public interface LoginRegisterView extends IView{
        void onRegisterData(RegisterBean registerBean);
        void onLoginData(LoginBean loginBean);
    }

    public static abstract class LoginRegisterPresenter extends BasePresenter<LoginRegisterView>{
        public abstract void register(String urlString);
        public abstract void login(String urlString);
    }
}
