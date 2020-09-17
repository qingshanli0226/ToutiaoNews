package com.example.toutiaonews.reg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.toutiaonews.R;

public class RegMainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnRegback;
    private EditText regUsername;
    private EditText regPwd;
    private EditText regPwdd;
    private Button btnReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_main);
        btnRegback = (ImageView) findViewById(R.id.btn_regback);
        regUsername = (EditText) findViewById(R.id.reg_username);
        regPwd = (EditText) findViewById(R.id.reg_pwd);
        regPwdd = (EditText) findViewById(R.id.reg_pwdd);
        btnReg = (Button) findViewById(R.id.btn_reg);
        btnReg.setOnClickListener(this);
        btnRegback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_regback:
                finish();
                break;
            case R.id.btn_reg:

                break;
        }
    }

}