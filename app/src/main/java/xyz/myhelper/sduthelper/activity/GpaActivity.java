package xyz.myhelper.sduthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.myhelper.sduthelper.BaseActivity;
import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.adapters.CetAdapter;
import xyz.myhelper.sduthelper.adapters.GpaAdapter;
import xyz.myhelper.sduthelper.bean.CetBean;
import xyz.myhelper.sduthelper.bean.GpaBean;
import xyz.myhelper.sduthelper.bean.StudentInfo;
import xyz.myhelper.sduthelper.dataparse.CetParse;
import xyz.myhelper.sduthelper.dataparse.GpaParse;
import xyz.myhelper.sduthelper.net.NetWorkSingleTon;
import xyz.myhelper.sduthelper.utils.GpaUtil;

public class GpaActivity extends BaseActivity {

    // ListView需要的组件
    private ListView mListView;
    private List<GpaBean> mLists;
    private GpaAdapter adapter;
    private File parentPath;
    private StudentInfo info;
    private TextView idShow; // 显示学生基本信息
    private TextView infoShow; // 显示专业
    private TextView gpaShow; // 显示绩点
    private Button exitBtn; // 退出按钮
    private Button cetBtn; // 四六级查询
    private ListView cetListView;
    private List<CetBean> cetList;
    private CetAdapter cetAdapter;
    public static final String CET_URL = "http://210.44.176.116/cjcx/stkcjcx_list.php";
    private String post_xuehao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
        initComponent();  // 设置组件
        initData(); // 初始化数据
        initView(); // 显示界面

    }

    private void initView() {
        List<GpaBean> tempList = GpaParse.getGPAList(parentPath, info);
        if (tempList != null) {
            mLists.addAll(tempList);
            adapter.notifyDataSetChanged();
            idShow.setText("姓名： " + info.getName() + "\n性别： " + info.getSex() +
                    "\n专业：" + info.getClase());
            infoShow.setText("\n学号： " + info.getXh());
            setGpa(); // 计算绩点的分数
        }
    }

    private void setGpa() {
        double[] d = new GpaUtil().getGpa(mLists);
        if (Double.isNaN(d[1])) {
            gpaShow.setText("您的绩点为：" + d[0]);
        } else {
            gpaShow.setText("您的第一学位的绩点：" + d[0] + "\n第二学位的绩点为：" + d[1]);
        }
    }


    private void initComponent() {
        mListView = (ListView) findViewById(R.id.gpa_show_list);
        idShow = (TextView) findViewById(R.id.allGPA_showId);
        infoShow = (TextView) findViewById(R.id.allGPA_showInfo);
        gpaShow = (TextView) findViewById(R.id.allGpa_showGpa);
        View headView = LayoutInflater.from(this).inflate(R.layout.listview_head_gpa, null);
        mListView.addHeaderView(headView);
        exitBtn = (Button) findViewById(R.id.gpa_exit);
        exitBtn.setOnClickListener(new BtnClick());
        cetBtn = (Button) findViewById(R.id.gpa_cet);
        cetBtn.setOnClickListener(new BtnClick());
        cetListView = (ListView) findViewById(R.id.cet_show_list);
        cetList = new ArrayList<>();
        cetAdapter = new CetAdapter(this, cetList);

    }

    class BtnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.gpa_exit:
                    finish();
                    break;
                case R.id.gpa_cet:
                    cetListView.setAdapter(cetAdapter);
                    cetListView.setVisibility(View.VISIBLE);
                    RequestQueue requestQueue = NetWorkSingleTon.getRequestQueue();
                    StringRequest request = new StringRequest(Request.Method.POST, CET_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String s) {
                            Handler handler = new Handler();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<CetBean> tempList;
                                      tempList = CetParse.getCetList(s);
                                        if (tempList != null) {
                                            cetList.addAll(tempList);

                                        }
                                    }
                                });

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("post_xuehao", post_xuehao);
                            return map;
                        }

                    };
                    requestQueue.add(request);
                    break;
            }
        }
    }

    private void initData() {
        Intent intent = getIntent();
        String filePath = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "sduthelper" + File.separator + "mygpa";
        post_xuehao = intent.getStringExtra("xh");
        parentPath = new File(filePath + File.separator + post_xuehao + ".html");
        info = new StudentInfo();

        // 设置适配器
        mLists = new ArrayList<>();
        adapter = new GpaAdapter(mLists, this);
        mListView.setAdapter(adapter);
    }


}
