package com.example.user.contract;

import com.example.common.mode.LoginOutBean;
import com.example.framework2.base.BasePresenter;
import com.example.framework2.base.IView;

public class LoginOutContract {

    public interface LoginOutView extends IView{
        void onLoginOutData(LoginOutBean loginOutBean);
    }

    public static abstract class LoginOutPresenter extends BasePresenter<LoginOutView>{
        public abstract void loginOut(String urlString);
    }

}
