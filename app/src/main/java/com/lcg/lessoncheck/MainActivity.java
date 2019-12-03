package com.lcg.lessoncheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
        LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_menu, bottomNavigationView, true);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.radius_image_view_home:
                break;
            case R.id.radius_image_view_message:
                break;
            case R.id.radius_image_view_info:
                break;
        }
    }
}
