package com.lcg.lessoncheck;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.sql.Connection;

public class LessonStudent extends Fragment implements View.OnClickListener {
    private QMUIRoundButton qmuiRoundButtonSelect;
    private QMUIRoundButton qmuiRoundButtonCheck;
    private String account;
    private boolean reslut = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.lesson_student, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        account = getArguments().getString(MainActivity.LOGIN_ACCOUNT);
        qmuiRoundButtonCheck = getActivity().findViewById(R.id.qrbtn_lesson_student_check);
        qmuiRoundButtonSelect = getActivity().findViewById(R.id.qrbtn_lesson_student_select);
        qmuiRoundButtonCheck.setOnClickListener(this);
        qmuiRoundButtonSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qrbtn_lesson_student_select:
                break;
            case R.id.qrbtn_lesson_student_check:
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
                builder.setTitle("请输入签到码")
                        .setPlaceholder("六位签到码")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .addAction("取消", new QMUIDialogAction.ActionListener(){

                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener(){

                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                final CharSequence text = builder.getEditText().getText();
                                if(text != null && text.length() > 0){
                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Connection conn = null;
                                            try {
                                                DBService dbService = DBService.getDbService();
                                                String key = dbService.getKey(account);
                                                if(key.equals(text.toString())){
                                                    //如果签到成功，设置签到码
                                                    dbService.setCheckFlag(account);
                                                    reslut = true;
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    thread.start();
                                    try {
                                        thread.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if(reslut){
                                        Toast.makeText(getContext(), "签到成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(getActivity(), "请输入签到码", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
                break;
        }
    }
}
