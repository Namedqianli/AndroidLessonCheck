package com.lcg.lessoncheck;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLogin;
    private EditText editTextAccount;
    private EditText editTextPassword;
    private CheckBox checkBoxRemember;
    private TextView textViewSigin;
    private ImageView imageViewVisbility;
    private Intent intent;
    private static final String LOGIN_ACCOUNT = "Login_Account";
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.USE_FULL_SCREEN_INTENT
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(msg.obj.toString().isEmpty()){
                        tipDialog.dismiss();
                        tipDialog = new QMUITipDialog.Builder(LoginActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                                .setTipWord("登录失败，账号或密码错误!")
                                .create();
                        tipDialog.show();
                        editTextAccount.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tipDialog.dismiss();
                            }
                        }, 1500);
                        break;
                    }
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    private Boolean bPwSeitch = false;
    private QMUITipDialog tipDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        buttonLogin = findViewById(R.id.qrbtn_login_login);
        editTextAccount = findViewById(R.id.edt_login_account);
        editTextPassword = findViewById(R.id.edt_login_password);
        checkBoxRemember = findViewById(R.id.cb_login_remember_password);
        textViewSigin = findViewById(R.id.tv_login_sigin);
        imageViewVisbility = findViewById(R.id.imv_login_visbility);
        buttonLogin.setOnClickListener(this);
        textViewSigin.setOnClickListener(this);
        imageViewVisbility.setOnClickListener(this);
        intent = new Intent();
        setPassword();
        getPermission();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login_sigin:
                //按下注册，跳转到注册按钮
                intent.setClass(LoginActivity.this, SiginActivity.class);
                startActivity(intent);
                break;
            case R.id.qrbtn_login_login:
                //按下登录键
                login();
                break;
            case R.id.imv_login_visbility:
                //密码可见/不可见
                bPwSeitch = !bPwSeitch;
                if(bPwSeitch){
                    imageViewVisbility.setImageResource(R.drawable.ic_visibility_black_24dp);
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    imageViewVisbility.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
                break;
        }
    }

    //处理登录操作
    private void login(){
        String spFileName = getResources().getString(R.string.shared_preferences_file_name);
        String accountKey = getResources().getString(R.string.login_account_name);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememberPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spFile.edit();

        if(editTextAccount.getText().toString().isEmpty()){
            //如果账号为空
            final QMUITipDialog tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("请输入账号！")
                    .create();
            tipDialog.show();
            editTextAccount.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 1000);
            return;
        }
        if(editTextPassword.getText().toString().isEmpty()){
            //如果密码为空
            final QMUITipDialog tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("请输入密码！")
                    .create();
            tipDialog.show();
            editTextPassword.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 1000);
            return;
        }
        if(checkBoxRemember.isChecked()){
            //保存密码选中，保存密码
            String password = editTextPassword.getText().toString();
            String account = editTextAccount.getText().toString();

            editor.putString(accountKey, account);
            editor.putString(passwordKey, password);
            editor.putBoolean(rememberPasswordKey, true);
            editor.apply();
        }else {
            //没选中，删除密码
            editor.remove(accountKey);
            editor.remove(passwordKey);
            editor.putBoolean(rememberPasswordKey, false);
            editor.apply();
        }
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("登录中")
                .create();
        tipDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    DBService dbService = DBService.getDbService();
                    String password = dbService.getPassword(editTextAccount.getText().toString());
                    Message message = new Message();
                    message.what = 1;
                    message.obj = password;
                    handler.sendMessage(message);
//                    System.setProperty("jdbc.driver","com.mysql.jdbc.Driver");
//                    java.sql.DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//                    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//                    new com.mysql.jdbc.Driver();
//                    Class.forName("com.mysql.jdbc.Driver");
//                    conn = DriverManager.getConnection("jdbc:mysql://118.25.88.42:3306/lessoncheck", "lesson", ".123456");//获取连接
//                    String password =
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
//        if(password != null){
//            tipDialog.dismiss();
//            editTextAccount.setText(password);
//        }
    }

    //程序启动时设置密码，账号
    private void setPassword(){
        String spFileName = getResources().getString(R.string.shared_preferences_file_name);
        String accountKey = getResources().getString(R.string.login_account_name);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememberPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spFile.edit();
        String account = spFile.getString(accountKey, null);
        String password = spFile.getString(passwordKey, null);
        Boolean rememberPassword = spFile.getBoolean(rememberPasswordKey, false);

        if(account != null && !TextUtils.isEmpty(account)){
            editTextAccount.setText(account);
        }
        if(password != null && !TextUtils.isEmpty(password)){
            editTextPassword.setText(password);
        }
        checkBoxRemember.setChecked(rememberPassword);
    }

    //申请权限
    private void getPermission(){
        //检测权限
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    LoginActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
            }else{
                requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        }
    }
}
