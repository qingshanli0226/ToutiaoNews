package com.example.toutiaonews.login;

import com.example.framework2.mvp.model.IModel;
import com.example.framework2.mvp.presenter.BasePresenter;
import com.example.framework2.mvp.view.IView;
import com.example.net.activity_bean.LoginBean;

import io.reactivex.Observable;

public class LoginContract {
    public interface ILoginView extends IView {
        void onLogin(LoginBean loginBean);
    }

    public interface ILoginModel extends IModel {
        void  login(String name,String pwd,Observable<LoginBean> observable);
    }
}
