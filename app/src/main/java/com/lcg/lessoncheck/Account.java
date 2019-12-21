package com.lcg.lessoncheck;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import static com.lcg.lessoncheck.MainActivity.*;

public class Account extends Fragment {
    private TextView textViewName;
    private TextView textViewType;
    private TextView textViewSchool;
    private TextView textViewId;
    private ImageView imageViewType;
    private String name;
    private String type;
    private String school;
    private String id;
    private QMUITipDialog qmuiTipDialog;
    private static final int UPDATE_NAME = 0;
    private static final int UPDATE_ID = 1;
    private static final int UPDATE_SCHOOL = 2;
    private static final int UPDATE_TYPE = 3;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_NAME:
                    textViewName.setText(msg.obj.toString());
                    break;
                case UPDATE_SCHOOL:
                    textViewSchool.setText(msg.obj.toString());
                    break;
                case UPDATE_ID:
                    textViewId.setText(msg.obj.toString());
                    break;
                case UPDATE_TYPE:
                    if(msg.obj.toString().equals("teacher")) {
                        //如果是老师
                        textViewType.setText("老师");
                        imageViewType.setImageResource(R.drawable.teacher);
                    }else {
                        //如梭是学生
                        textViewType.setText("学生");
                        imageViewType.setImageResource(R.drawable.student);
                    }
                    qmuiTipDialog.dismiss();
                    break;
            }
        }
    };

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
        imageViewType = getActivity().findViewById(R.id.imv_account_identity);
    }

    //更新显示
    private void updateView(){
        final String account = getArguments().getString(LOGIN_ACCOUNT);
        qmuiTipDialog = new QMUITipDialog.Builder(getActivity())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在获取信息")
                .create();
        qmuiTipDialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DBService dbService = DBService.getDbService();
                Message message1 = Message.obtain();
                name = dbService.getName(account);
                message1.what = UPDATE_NAME;
                message1.obj = name;
                handler.sendMessage(message1);
                Message message2 = Message.obtain();
                id = dbService.getId(account);
                message2.what = UPDATE_ID;
                message2.obj = id;
                handler.sendMessage(message2);
                Message message3 = Message.obtain();
                school = dbService.getSchool(account);
                message3.what = UPDATE_SCHOOL;
                message3.obj = school;
                handler.sendMessage(message3);
                Message message4 = Message.obtain();
                type = dbService.getAccountType(account);
                message4.what = UPDATE_TYPE;
                message4.obj = type;
                handler.sendMessage(message4);
            }
        });
        thread.start();
    }
}
