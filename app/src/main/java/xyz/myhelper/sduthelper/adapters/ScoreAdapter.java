package xyz.myhelper.sduthelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.bean.ScoreBean;

/**
 * Created by dream on 15-11-23.
 */
public class ScoreAdapter extends BaseAdapter {
    private LayoutInflater mInflate;
    private List<ScoreBean> mList;
    public ScoreAdapter(Context context, List<ScoreBean> mList){
        mInflate = LayoutInflater.from(context);
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler vh;
        if (convertView == null){
            vh = new ViewHodler();
            convertView = mInflate.inflate(R.layout.listview_item_score, parent, false);
            vh.subject = (TextView) convertView.findViewById(R.id.item_score_subject);
            vh.type = (TextView) convertView.findViewById(R.id.item_score_type);
            vh.grade = (TextView) convertView.findViewById(R.id.item_score_grade);
            vh.ordinary = (TextView) convertView.findViewById(R.id.item_score_ori);
            vh.midterm = (TextView) convertView.findViewById(R.id.item_score_mid);
            vh.end = (TextView) convertView.findViewById(R.id.item_score_end);
            vh.test = (TextView) convertView.findViewById(R.id.item_score_test);
            vh.score = (TextView) convertView.findViewById(R.id.item_score_score);
            vh.isAgain = (TextView) convertView.findViewById(R.id.item_score_is);
            vh.college = (TextView) convertView.findViewById(R.id.item_score_college);
            convertView.setTag(vh);
        }else {
            vh = (ViewHodler) convertView.getTag();
        }
        ScoreBean scoreBean = mList.get(position);
        vh.subject.setText(scoreBean.getSubject());
        vh.type.setText(scoreBean.getType());
        vh.grade.setText(scoreBean.getGrade());
        vh.ordinary.setText(scoreBean.getOrdinary());
        vh.midterm.setText(scoreBean.getMidterm());
        vh.end.setText(scoreBean.getEnd());
        vh.test.setText(scoreBean.getTest());
        vh.score.setText(scoreBean.getScore());
        vh.isAgain.setText(scoreBean.getIsAgain());
        vh.college.setText(scoreBean.getCollege());
        return convertView;
    }

    class ViewHodler{
        private TextView subject;//课程名称
        private TextView type; // 课程性质
        private TextView grade; // 学分
        private TextView ordinary; // 平时成绩
        private TextView midterm; //期中成绩
        private TextView end; //期末成绩
        private TextView test; // 实验成绩
        private TextView score; // 成绩
        private TextView isAgain; // 是否重修
        private TextView college; // 开课学院
    }
}
