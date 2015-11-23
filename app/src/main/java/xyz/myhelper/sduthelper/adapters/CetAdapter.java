package xyz.myhelper.sduthelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.bean.CetBean;


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
            vh.id = (TextView) convertView.findViewById(R.id.item_cet_id);
            vh.num = (TextView) convertView.findViewById(R.id.item_cet_num);
            vh.time = (TextView) convertView.findViewById(R.id.item_cet_time);
            vh.type = (TextView) convertView.findViewById(R.id.item_cet_type);
            vh.score = (TextView) convertView.findViewById(R.id.item_cet_score);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        CetBean cet = mLists.get(position);
        vh.id.setText(cet.getId());
        vh.num.setText(cet.getNum());
        vh.time.setText(cet.getTime());
        vh.type.setText(cet.getType());
        vh.score.setText(cet.getScore());
        return null;
    }

    class ViewHolder{
        TextView id,num,time,type,score;
    }
}
