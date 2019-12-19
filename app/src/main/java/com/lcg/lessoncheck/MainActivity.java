package com.lcg.lessoncheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mysql.jdbc.Constants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private BottomNavigationView bottomNavigationView;
    private ImageView imageViewLesson;  //导航栏：课程
    private ImageView imageViewAccount; //导航栏：帐号
    private Fragment fragmentAccount;   //帐号页面
    private Fragment fragmentLesson;    //课程页面
    private Fragment fragmentMessage;   //信息页面
    private Fragment[] fragments;       //用于页面的切换
    private String account;             //记录登录的帐号
    private int lastFragment;           //记录页面号
    public static final String LOGIN_ACCOUNT = "Login_Account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
        LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_menu, bottomNavigationView, true);
        bottomNavigationView.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        account = intent.getStringExtra(LoginActivity.LOGIN_ACCOUNT);
        init();
        initFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, fragmentLesson).show(fragmentLesson).commit();
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_SHORT).show();
        switch (view.getId()){
            case R.id.radius_image_view_home:
                if(lastFragment != 0){
                    switchFragment(lastFragment, 0);
                    lastFragment = 0;
                }
                break;
            case R.id.radius_image_view_message:
                break;
            case R.id.radius_image_view_info:
                if(lastFragment != 1){
                    switchFragment(lastFragment,1);
                    lastFragment = 1;
                }
                break;
        }
    }

    //初始化
    private void init(){
        imageViewLesson = findViewById(R.id.radius_image_view_home);
        imageViewAccount = findViewById(R.id.radius_image_view_info);

        //设置事件监听
        imageViewLesson.setOnClickListener(this);
        imageViewAccount.setOnClickListener(this);
    }

    //初始化各个页面
    private void initFragment(){
        Bundle bundle = new Bundle();
        bundle.putString(LOGIN_ACCOUNT, account);
        fragmentAccount = new Account();
        fragmentLesson = new Lesson();

        fragmentAccount.setArguments(bundle);
        fragmentLesson.setArguments(bundle);

        fragments = new Fragment[]{fragmentLesson, fragmentAccount};
        lastFragment = 0;
    }

    //切换页面
    private void switchFragment(int lastFragment, int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //隐藏上一个页面
        transaction.hide(fragments[lastFragment]);
        //显示下一个页面
        if(fragments[index].isAdded() == false){
            transaction.add(R.id.main_view, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}
