package xyz.myhelper.sduthelper.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.bean.LessonBean;
import xyz.myhelper.sduthelper.bean.ScheduleBean;

/**
 * 课表的适配器
 * Created by dream on 15-11-18.
 */

public class ScheduleAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ScheduleBean> mLists;

    public ScheduleAdapter(Context context, List<ScheduleBean> mList) {
        inflater = LayoutInflater.from(context);
        this.mLists = mList;
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
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            // 对应行
            convertView = inflater.inflate(R.layout.listview_item_schedule, parent, false);
            vh.list_0 = (TextView) convertView.findViewById(R.id.listview_item_schedule_list_0);
            vh.list_1 = (TextView) convertView.findViewById(R.id.listview_item_schedule_list_1);
            vh.list_2 = (TextView) convertView.findViewById(R.id.listview_item_schedule_list_2);
            vh.list_3 = (TextView) convertView.findViewById(R.id.listview_item_schedule_list_3);
            vh.list_4 = (TextView) convertView.findViewById(R.id.listview_item_schedule_list_4);
            vh.list_5 = (TextView) convertView.findViewById(R.id.listview_item_schedule_list_5);
            vh.list_6 = (TextView) convertView.findViewById(R.id.listview_item_schedule_list_6);
            vh.list_7 = (TextView) convertView.findViewById(R.id.listview_item_schedule_list_7);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ScheduleBean schedule = mLists.get(position);
        vh.list_0.setText(schedule.getPitchNumber());
        if (schedule.getMonLesson() != null && !"".equals(schedule.getMonLesson())){
            LessonBean[] lessonBean = schedule.getMonLesson();
            if (lessonBean != null && lessonBean.length != 0){
                if (lessonBean.length == 1) {
                    // 该节课只有一天，该控件不恩能够点击
                    vh.list_1.setClickable(false);
                    vh.list_1.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_1.setBackgroundColor(Color.RED);
                    vh.list_1.setTextColor(Color.WHITE);
                }else {
                    vh.list_1.setClickable(true);
                    vh.list_1.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_1.setBackgroundColor(Color.BLUE);
                    vh.list_1.setTextColor(Color.WHITE);
                    vh.list_1.setOnClickListener(new TextClick());
                }
            }else {
                vh.list_1.setTextColor(Color.WHITE);
                vh.list_1.setText("");
                vh.list_1.setBackgroundColor(Color.WHITE);
            }
        }

        if (schedule.getTueLesson() != null && !"".equals(schedule.getTueLesson())){
            LessonBean[] lessonBean = schedule.getTueLesson();
            if (lessonBean != null && lessonBean.length != 0){
                if (lessonBean.length == 1) {
                    // 该节课只有一天，该控件不恩能够点击
                    vh.list_2.setClickable(false);
                    vh.list_2.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_2.setBackgroundColor(Color.RED);
                    vh.list_2.setTextColor(Color.WHITE);
                }else {
                    vh.list_2.setClickable(true);
                    vh.list_2.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_2.setBackgroundColor(Color.BLUE);
                    vh.list_2.setTextColor(Color.WHITE);
                }

            }else {
                vh.list_2.setTextColor(Color.WHITE);
                vh.list_2.setText("");
                vh.list_2.setBackgroundColor(Color.WHITE);
            }
        }

        if (schedule.getWedLesson() != null && !"".equals(schedule.getWedLesson())){
            LessonBean[] lessonBean = schedule.getWedLesson();
            if (lessonBean != null && lessonBean.length != 0){
                if (lessonBean.length == 1) {
                    // 该节课只有一天，该控件不恩能够点击
                    vh.list_3.setClickable(false);
                    vh.list_3.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_3.setBackgroundColor(Color.RED);
                    vh.list_3.setTextColor(Color.WHITE);
                }else {
                    vh.list_3.setClickable(true);
                    vh.list_3.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_3.setBackgroundColor(Color.BLUE);
                    vh.list_3.setTextColor(Color.WHITE);
                }

            }else {
                vh.list_3.setTextColor(Color.WHITE);
                vh.list_3.setText("");
                vh.list_3.setBackgroundColor(Color.WHITE);
            }
        }

        if (schedule.getThuLesson() != null && !"".equals(schedule.getThuLesson())){
            LessonBean[] lessonBean = schedule.getThuLesson();
            if (lessonBean != null && lessonBean.length != 0){
                if (lessonBean.length == 1) {
                    // 该节课只有一天，该控件不恩能够点击
                    vh.list_4.setClickable(false);
                    vh.list_4.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_4.setBackgroundColor(Color.RED);
                    vh.list_4.setTextColor(Color.WHITE);
                }else {
                    vh.list_4.setClickable(true);
                    vh.list_4.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_4.setBackgroundColor(Color.BLUE);
                    vh.list_4.setTextColor(Color.WHITE);
                }

            }else {
                vh.list_4.setTextColor(Color.WHITE);
                vh.list_4.setText("");
                vh.list_4.setBackgroundColor(Color.WHITE);
            }
        }

        if (schedule.getFriLesson() != null && !"".equals(schedule.getFriLesson())){
            LessonBean[] lessonBean = schedule.getFriLesson();
            if (lessonBean != null && lessonBean.length != 0){
                if (lessonBean.length == 1) {
                    // 该节课只有一天，该控件不恩能够点击
                    vh.list_5.setClickable(false);
                    vh.list_5.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_5.setBackgroundColor(Color.RED);
                    vh.list_5.setTextColor(Color.WHITE);
                }else {
                    vh.list_5.setClickable(true);
                    vh.list_5.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_5.setBackgroundColor(Color.BLUE);
                    vh.list_5.setTextColor(Color.WHITE);
                }

            }else {
                vh.list_5.setTextColor(Color.WHITE);
                vh.list_5.setText("");
                vh.list_5.setBackgroundColor(Color.WHITE);
            }
        }

        if (schedule.getSatLesson() != null && !"".equals(schedule.getSatLesson())){
            LessonBean[] lessonBean = schedule.getSatLesson();
            if (lessonBean != null && lessonBean.length != 0){
                if (lessonBean.length == 1) {
                    // 该节课只有一天，该控件不恩能够点击
                    vh.list_6.setClickable(false);
                    vh.list_6.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_6.setBackgroundColor(Color.RED);
                    vh.list_6.setTextColor(Color.WHITE);
                }else {
                    vh.list_6.setClickable(true);
                    vh.list_6.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_6.setBackgroundColor(Color.BLUE);
                    vh.list_6.setTextColor(Color.WHITE);
                }

            }else {
                vh.list_6.setTextColor(Color.WHITE);
                vh.list_6.setText("");
                vh.list_6.setBackgroundColor(Color.WHITE);
            }
        }

        if (schedule.getSunLesson() != null && !"".equals(schedule.getSunLesson())){
            LessonBean[] lessonBean = schedule.getSunLesson();
            if (lessonBean != null && lessonBean.length != 0){
                if (lessonBean.length == 1) {
                    // 该节课只有一天，该控件不恩能够点击
                    vh.list_7.setClickable(false);
                    vh.list_7.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_7.setBackgroundColor(Color.RED);
                    vh.list_7.setTextColor(Color.WHITE);
                }else {
                    vh.list_7.setClickable(true);
                    vh.list_7.setText(lessonBean[0].getSubject() + lessonBean[0].getTime() +
                            lessonBean[0].getTecher() + lessonBean[0].getPlace());
                    vh.list_7.setBackgroundColor(Color.BLUE);
                    vh.list_7.setTextColor(Color.WHITE);
                }

            }else {
                vh.list_7.setTextColor(Color.WHITE);
                vh.list_7.setText("");
                vh.list_7.setBackgroundColor(Color.WHITE);
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView list_0, list_1, list_2, list_3, list_4, list_5, list_6, list_7;
    }

    class TextClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.listview_item_schedule_list_1:
                    // 弹出对话框
                    break;
            }
        }
    }
}
