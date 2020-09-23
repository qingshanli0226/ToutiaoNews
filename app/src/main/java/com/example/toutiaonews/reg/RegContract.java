package com.example.toutiaonews.reg;

import com.example.framework.bean.BasePresenter;
import com.example.framework.bean.IView;
import com.example.net.activity_bean.RegisterBean;


public class RegContract {
    public interface IRegView extends IView {
        void onReg(RegisterBean registerBean);
    }

    public static abstract class RegPresenter extends BasePresenter<IRegView> {
        public abstract void reg(String name, String password);
    }
}
