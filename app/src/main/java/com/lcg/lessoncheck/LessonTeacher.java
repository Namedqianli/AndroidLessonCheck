package com.lcg.lessoncheck;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class LessonTeacher extends Fragment implements View.OnClickListener {
    private ListView listView;
    private String account;
    private QMUIRoundButton qmuiRoundButtonCheck;
    private ArrayList<Student> studentList;
    private boolean flag = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.lesson_teacher, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        account = getArguments().getString(MainActivity.LOGIN_ACCOUNT);
        qmuiRoundButtonCheck = getActivity().findViewById(R.id.qrbtn_lesson_check);
        qmuiRoundButtonCheck.setText("开始签到");
        qmuiRoundButtonCheck.setOnClickListener(this);
        listView = getActivity().findViewById(R.id.lv_lesson);
        studentList = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qrbtn_lesson_check:
                dealCheck(flag);
                break;
        }
    }

    public void dealCheck(boolean isStart){
        if(qmuiRoundButtonCheck.getText().toString().equals("开始签到")){
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
                                            dbService.setKey(text.toString());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                qmuiRoundButtonCheck.setText("结束签到");
                                Toast.makeText(getContext(), "开始签到", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(getActivity(), "请输入签到码", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).show();
        }else {
            qmuiRoundButtonCheck.setText("开始签到");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Connection conn = null;
                    try {
                        DBService dbService = DBService.getDbService();
                        ResultSet resultSet = dbService.getCheck();
                        while(resultSet.next()){
                            ResultSet resultSet1 = dbService.getCheckStudentInfo(resultSet.getString("studentaccount"));
                            if(!resultSet.getString("key").equals("1")){
                                continue;
                            }
                            if(resultSet1.next()){
                                Student student = new Student(resultSet1.getString("name"), resultSet1.getString("id"), "签到成功");
                                studentList.add(student);
                            }
                        }
                        dbService.setKey("0");
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
            studentAdapter studentAdapter = new studentAdapter();
            listView.setAdapter(studentAdapter);
        }
    }

    public class studentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return studentList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View view1;
            if(view == null){
                LayoutInflater inflater = LessonTeacher.this.getLayoutInflater();
                view1 = inflater.inflate(R.layout.lesson_iteam, null);
            }else {
                view1 = view;
            }
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewName = view1.findViewById(R.id.tv_lesson_item_name);
            viewHolder.textViewId = view1.findViewById(R.id.tv_lesson_item_id);
            viewHolder.textViewStatus = view1.findViewById(R.id.tv_lesson_item_status);
            Student st = studentList.get(i);
            viewHolder.textViewName.setText(st.getName());
            viewHolder.textViewId.setText(st.getId());
            viewHolder.textViewStatus.setText(st.getStatus());

            return view1;
        }

        public class ViewHolder {
            TextView textViewName;
            TextView textViewId;
            TextView textViewStatus;
        }
    }

    public class Student{
        private String name;
        private String id;
        private String status;

        public Student(String name, String id, String status){
            this.name = name;
            this.id = id;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}