package com.example.toutiaonews.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farmework.base.BaseMVPActivity;
import com.example.promptpagemodule.promptpage.promptpageview.PromptPageViewHolder;
import com.example.promptpagemodule.promptpage.promptpageview.PromptView;
import com.example.toutiaonews.R;
import com.example.toutiaonews.bean.RegisterEntity;
import com.example.toutiaonews.contract.RegisterContract;
import com.example.toutiaonews.presenter.RegisterPresenter;

public class RegisterActivity extends BaseMVPActivity<RegisterPresenter, RegisterContract.registerView> implements RegisterContract.registerView {
    private EditText username;
    private EditText pas;
    private Button register;
    private String usernamestr;
    private String passtr;
    private PromptView ptpv;
    private PromptPageViewHolder pageViewHolder;
    @Override
    protected void initPresenter() {
        iHttpPresenter = new RegisterPresenter();
    }

    @Override
    protected void initHttpData() {
        pageViewHolder=new PromptPageViewHolder(this);
        ptpv.setHolder(pageViewHolder);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernamestr = username.getText().toString().trim();
                passtr = pas.getText().toString().trim();
                iHttpPresenter.getRegisterData(usernamestr, passtr);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int bandLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        username = (EditText) findViewById(R.id.username);
        pas = (EditText) findViewById(R.id.pas);
        register = (Button) findViewById(R.id.register);
        ptpv = (PromptView) findViewById(R.id.ptpv);
    }

    @Override
    public void onRegisterData(RegisterEntity registerEntity) {
        Toast.makeText(RegisterActivity.this, registerEntity.getMessage(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showError(String code, String message) {
        ptpv.showEmptyView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
