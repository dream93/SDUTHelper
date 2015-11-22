package xyz.myhelper.sduthelper.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.bean.GpaBean;

/**
 * Created by dream on 15-11-8.
 */
public class GpaAdapter extends BaseAdapter {
    private List<GpaBean> mLists;
    private LayoutInflater inflater;

    public GpaAdapter(List<GpaBean> mLists, Context contex) {
        this.mLists = mLists;
        inflater = LayoutInflater.from(contex);
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int position) {

        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_gpa, null);
            viewHolder = new ViewHolder();
            viewHolder.serial = (TextView) convertView.findViewById(R.id.gpaList_serialNum);
            viewHolder.subject = (TextView) convertView.findViewById(R.id.gpaList_subjectName);
            viewHolder.grade = (TextView) convertView.findViewById(R.id.gpaList_gradeScore);
            viewHolder.score = (TextView) convertView.findViewById(R.id.gpaList_grade);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GpaBean gpa = mLists.get(position);
        viewHolder.serial.setText(gpa.getSerial());
        viewHolder.subject.setText(gpa.getSubject());
        viewHolder.grade.setText(gpa.getGrade());
        if (gpa.getNewGrade() != null && !"".equals(gpa.getNewGrade())) {
            if ("缺考".equals(gpa.getNewGrade())){
                viewHolder.score.setBackgroundColor(Color.RED);
            }else if (Double.parseDouble(gpa.getNewGrade()) < 60) {
                viewHolder.score.setBackgroundColor(Color.RED);
            } else {
                viewHolder.score.setBackgroundColor(Color.WHITE);
            }
        }else {
            viewHolder.score.setBackgroundColor(Color.WHITE);
        }
        if (gpa.getNewGrade() != null && !" ".equals(gpa.getNewGrade()) && !"".equals(gpa.getNewGrade())) {
            viewHolder.score.setText(gpa.getOriginalGrade() + "/" + gpa.getNewGrade());
        }else {
            viewHolder.score.setText(gpa.getOriginalGrade());
        }

        if ("第二".equals(gpa.getMethod()) || "第二专业".equals(gpa.getMethod())){
            viewHolder.subject.setBackgroundColor(Color.GRAY);
        }else if ("公选课".equals(gpa.getClasses())){
            viewHolder.subject.setBackgroundColor(Color.CYAN);
        }else {
            viewHolder.subject.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView serial, subject, grade, score;
    }
}
