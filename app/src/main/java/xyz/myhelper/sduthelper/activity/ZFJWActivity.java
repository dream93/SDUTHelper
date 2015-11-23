package xyz.myhelper.sduthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.List;

import xyz.myhelper.sduthelper.BaseActivity;
import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.adapters.ScoreAdapter;
import xyz.myhelper.sduthelper.bean.ScoreBean;
import xyz.myhelper.sduthelper.dataparse.ScoreParse;
import xyz.myhelper.sduthelper.net.NetWorkSingleTon;
import xyz.myhelper.sduthelper.utils.LogUtil;
import xyz.myhelper.sduthelper.utils.zfutils.net.MyHttpClientUtil;
import xyz.myhelper.sduthelper.utils.zfutils.utils.MyZF;

/**
 * @author dream
 *         成绩查询的类
 *         Created by dream on 15-11-22.
 * @version 1.0
 */
public class ZFJWActivity extends BaseActivity {

    public static final String PATH_URL = "http://210.44.176/xscjxc_dq.aspx?";
    // 成绩展示的ListView
    private ListView mListView;
    private List<ScoreBean> mList;
    private ScoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfjw);
        initComponent();
        initData();
    }

    private void initComponent() {
        mList = new ArrayList<>();
        adapter = new ScoreAdapter(this, mList);
        mListView = (ListView) findViewById(R.id.zfjw_cj_listView);
        mListView.setAdapter(adapter);
    }

    private void initData() {
        Intent intent = getIntent();
        String scoreInfo = intent.getStringExtra("scoreInfo");
        List<ScoreBean> tempList = ScoreParse.getScoreList(scoreInfo);
        if (tempList != null) {
            mList.addAll(tempList);
        }
        adapter.notifyDataSetChanged();
    }
}
