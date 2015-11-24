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
import xyz.myhelper.sduthelper.bean.CetBean;
import xyz.myhelper.sduthelper.utils.LogUtil;


/**
 * Created by dream on 15-11-23.
 */
public class CetAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CetBean> mLists;
    public CetAdapter(Context context, List<CetBean> mLists){
        mInflater = LayoutInflater.from(context);
        this.mLists = mLists;
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
        if (convertView == null){
            vh = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_item_cet, parent, false);
            vh.num = (TextView) convertView.findViewById(R.id.item_cet_num);
            vh.id = (TextView) convertView.findViewById(R.id.item_cet_id);
            vh.time = (TextView) convertView.findViewById(R.id.item_cet_time);
            vh.type = (TextView) convertView.findViewById(R.id.item_cet_type);
            vh.score = (TextView) convertView.findViewById(R.id.item_cet_score);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        CetBean cet = mLists.get(position);
        vh.num.setText(cet.getNum());
        vh.id.setText(cet.getId());

        String temp = cet.getTime();
        try{
            temp = temp.substring(0,6);
        }catch (Exception e){
            LogUtil.setNoClassLog("不用转换");
        }
        vh.time.setText(temp);
        vh.type.setText(cet.getType());
        vh.score.setText(cet.getScore());
      /*  try {
            double score = Double.parseDouble(cet.getScore());
            if (score < 300) {
                vh.score.setBackgroundColor(Color.RED);
            } else if (score < 425) {
                vh.score.setBackgroundColor(Color.BLACK);
            } else {
                vh.score.setBackgroundColor(Color.GREEN);
            }
        }catch (Exception e){
            LogUtil.setNoClassLog("无需转换");
        }*/
        return convertView;
    }

    class ViewHolder{
        TextView id,num,time,type,score;
    }
}
