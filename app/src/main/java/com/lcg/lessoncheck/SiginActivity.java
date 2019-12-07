package com.lcg.lessoncheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class SiginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSigin;
    private Button buttonBack;
    private EditText editTextAccount;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextId;
    private EditText editTextSchool;
    private CheckBox checkBoxTeacher;
    private CheckBox checkBoxStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sigin);

        //绑定控件
        buttonSigin = findViewById(R.id.qrbtn_sigin_sigin);
        buttonBack = findViewById(R.id.qrbtn_sigin_back);
        editTextAccount = findViewById(R.id.edt_sigin_account);
        editTextPassword = findViewById(R.id.edt_sigin_password);
        editTextName = findViewById(R.id.edt_sigin_name);
        editTextId = findViewById(R.id.edt_sigin_id);
        editTextSchool = findViewById(R.id.edt_sigin_school);
        checkBoxTeacher = findViewById(R.id.cb_sigin_teacher);
        checkBoxStudent = findViewById(R.id.cb_sigin_student);
        //事件监听
        buttonSigin.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        checkBoxStudent.setOnClickListener(this);
        checkBoxTeacher.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.qrbtn_sigin_back:
                //返回键按下跳转到登录界面
                Intent intent = new Intent(SiginActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.cb_sigin_student:
                //学生选中
                if(checkBoxTeacher.isChecked()){
                    checkBoxTeacher.setChecked(false);
                }
                break;
            case R.id.cb_sigin_teacher:
                //老师选中
                if(checkBoxStudent.isChecked()){
                    checkBoxStudent.setChecked(false);
                }
                break;
            case R.id.qrbtn_sigin_sigin:
                //注册
                dealSgin();
        }
    }
    private void dealSgin(){
        String account = editTextAccount.getText().toString();
        String password = editTextPassword.getText().toString();
        String name = editTextName.getText().toString();
        String id = editTextId.getText().toString();
        String school = editTextSchool.getText().toString();
        String identif = "";

        if(account.isEmpty()){
            //账号为空
            final QMUITipDialog tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("请输入账号")
                    .create();
            tipDialog.show();
            editTextAccount.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 1500);
        }else if(password.isEmpty()){
            //密码为空
            final QMUITipDialog tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("请输入密码")
                    .create();
            tipDialog.show();
            editTextPassword.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 1500);
        }else if(name.isEmpty()){
            //名字为空

        }
        if(checkBoxStudent.isChecked()){
            identif = "student";
        }else if(checkBoxTeacher.isChecked()){
            identif = "teacher";
        }else {

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBService dbService = DBService.getDbService();
                dbService.sigin("1354654", "231846515", "1611544", "alsdk", "guet", "teacher");
            }
        }).start();
    }
}
