package com.lcg.lessoncheck;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

public class CourseAdapter extends CursorAdapter {
    private Context cContext;
    private LayoutInflater layoutInflater;

    //构造函数
    public CourseAdapter(Context context){
        super(context, null, 0);
        cContext = context;
        layoutInflater = LayoutInflater.from(cContext);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View itemView = layoutInflater.inflate(R.layout.lesson_iteam, parent, false);

        if(itemView != null){
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewLessonName = itemView.findViewById(R.id.tv_lesson_item_name);
            viewHolder.textViewLessonInfo = itemView.findViewById(R.id.tv_lesson_item_info);
            viewHolder.textViewTeacher = itemView.findViewById(R.id.tv_lesson_item_techer);
            viewHolder.imageViewMenu = itemView.findViewById(R.id.imv_lesson_item_menu);

            return itemView;
        }

        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();

        int courseNameIndex = cursor.getColumnIndex("coursename");
        int teacherNameIndex = cursor.getColumnIndex("teachername");
        int describeIndex = cursor.getColumnIndex("describe");

        String courseName = cursor.getString(courseNameIndex);
        String teacherName = cursor.getString(teacherNameIndex);
        String describe = cursor.getString(describeIndex);

        int position = cursor.getPosition();

        if(viewHolder != null){
            viewHolder.textViewLessonName.setText(courseName);
            viewHolder.textViewTeacher.setText(teacherName);
            viewHolder.textViewLessonInfo.setText(describe);
        }
    }

    public class ViewHolder {
        TextView textViewLessonName;
        TextView textViewLessonInfo;
        TextView textViewTeacher;
        ImageView imageViewMenu;
    }
}
