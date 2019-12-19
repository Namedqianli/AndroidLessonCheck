package com.lcg.lessoncheck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.lcg.lessoncheck.MainActivity.*;

public class Account extends Fragment {
    private TextView textViewName;
    private TextView textViewType;
    private TextView textViewSchool;
    private TextView textViewId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.account, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        updateView();
    }

    //初始化
    private void init(){
        textViewName = getActivity().findViewById(R.id.tv_account_name);
        textViewId = getActivity().findViewById(R.id.tv_account_Id);
        textViewSchool = getActivity().findViewById(R.id.tv_account_school);
        textViewType = getActivity().findViewById(R.id.tv_account_identity);
    }

    //更新显示
    private void updateView(){
        final String account = getArguments().getString(LOGIN_ACCOUNT);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBService dbService = DBService.getDbService();
                textViewName.setText(dbService.getName(account));
                textViewId.setText(dbService.getId(account));
                textViewSchool.setText(dbService.getSchool(account));
                textViewType.setText(dbService.getAccountType(account));
            }
        }).start();
    }
}
