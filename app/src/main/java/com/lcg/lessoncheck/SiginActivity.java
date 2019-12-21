package com.lcg.lessoncheck;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

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
    private ImageView imageViewVisbility;
    private boolean bPwSeitch = false;
    private boolean siginFlag = false;

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
        imageViewVisbility = findViewById(R.id.imv_sigin_visbility);
        //事件监听
        buttonSigin.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        checkBoxStudent.setOnClickListener(this);
        checkBoxTeacher.setOnClickListener(this);
        imageViewVisbility.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.qrbtn_sigin_back:
                //返回键按下跳转到登录界面
                Intent intent = new Intent(SiginActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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
                break;
            case R.id.imv_sigin_visbility:
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
    private void dealSgin(){
        final String account = editTextAccount.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String name = editTextName.getText().toString();
        final String id = editTextId.getText().toString();
        final String school = editTextSchool.getText().toString();

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
            return;
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
            return;
        }else if(name.isEmpty()){
            //名字为空
            final QMUITipDialog tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("请输入名字")
                    .create();
            tipDialog.show();
            editTextName.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 1500);
            return;
        }else if(id.isEmpty()){
            //id为空
            final QMUITipDialog tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("请输入学号/工号")
                    .create();
            tipDialog.show();
            editTextId.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 1500);
            return;
        }else if(school.isEmpty()){
            //学校为空
            final QMUITipDialog tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("请输入学校")
                    .create();
            tipDialog.show();
            editTextSchool.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 1500);
            return;
        } else if(!checkBoxStudent.isChecked() && !checkBoxTeacher.isChecked()){
            //未选择身份
            final QMUITipDialog tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("请选择身份")
                    .create();
            tipDialog.show();
            checkBoxTeacher.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 1500);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String identif = "";
                if(checkBoxStudent.isChecked()){
                    identif = "student";
                }else if(checkBoxTeacher.isChecked()){
                    identif = "teacher";
                }
                DBService dbService = DBService.getDbService();
                siginFlag = dbService.sigin(account, password, id, name, school, identif);
            }
        }).start();
    }
}
