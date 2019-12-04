package com.lcg.lessoncheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLogin;
    private EditText editTextAccount;
    private EditText editTextPassword;
    private CheckBox checkBoxRemember;
    private TextView textViewSigin;
    private Intent intent;
    private static final String LOGIN_ACCOUNT = "Login_Account";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        buttonLogin = findViewById(R.id.qrbtn_login_login);
        editTextAccount = findViewById(R.id.edt_login_account);
        editTextPassword = findViewById(R.id.edt_login_password);
        checkBoxRemember = findViewById(R.id.cb_login_remember_password);
        textViewSigin = findViewById(R.id.tv_login_sigin);
        buttonLogin.setOnClickListener(this);
        textViewSigin.setOnClickListener(this);
        intent = new Intent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login_sigin:
                //按下注册，跳转到注册按钮
                intent.setClass(LoginActivity.this, SiginActivity.class);
                startActivity(intent);
        }
    }
}
