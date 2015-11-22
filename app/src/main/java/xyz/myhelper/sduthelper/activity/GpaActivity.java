package xyz.myhelper.sduthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import xyz.myhelper.sduthelper.BaseActivity;
import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.adapters.GpaAdapter;
import xyz.myhelper.sduthelper.bean.GpaBean;
import xyz.myhelper.sduthelper.bean.StudentInfo;
import xyz.myhelper.sduthelper.dataparse.GpaParse;
import xyz.myhelper.sduthelper.utils.GpaUtil;

public class GpaActivity extends BaseActivity {

    // ListView需要的组件
    private ListView mListView;
    private List<GpaBean> mLists;
    private GpaAdapter adapter;
    private String xh;
    private File parentPath;
    private StudentInfo info;
    private TextView idShow; // 显示学生基本信息
    private TextView infoShow; // 显示专业
    private TextView gpaShow; // 显示绩点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
        initComponent();  // 设置组件
        initData(); // 初始化数据
        initView(); // 显示界面

    }

    private void initView() {
        List<GpaBean> tempList= GpaParse.getGPAList(parentPath, info);
        if (tempList != null){
            mLists.addAll(tempList);
            adapter.notifyDataSetChanged();
            idShow.setText("姓名： " + info.getName() + "  学号： " + info.getXh()
                    + "  性别： " + info.getSex());
            infoShow.setText("专业：" + info.getClase());
            setGpa(); // 计算绩点的分数
        }
    }

    private void setGpa() {
        double[] d = new GpaUtil().getGpa(mLists);
        if (Double.isNaN(d[1])){
            gpaShow.setText("您的绩点为：" + d[0]);
        }else{
            gpaShow.setText("您的第一学位的绩点："+ d[0] + "\n第二学位的绩点为：" + d[1]);
        }
    }


    private void initComponent() {
        mListView = (ListView) findViewById(R.id.gpa_show_list);
        idShow = (TextView) findViewById(R.id.allGPA_showId);
        infoShow = (TextView) findViewById(R.id.allGPA_showInfo);
        gpaShow = (TextView) findViewById(R.id.allGpa_showGpa);
        View headView = LayoutInflater.from(this).inflate(R.layout.listview_head_gpa, null);
        mListView.addHeaderView(headView);
    }

    private void initData() {
        Intent intent = getIntent();
        xh = intent.getStringExtra("xh");
        String filePath = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "sduthelper" + File.separator + "mygpa";
        parentPath = new File(filePath + File.separator + xh + ".html");
        info = new StudentInfo();

        // 设置适配器
        mLists = new ArrayList<>();
        adapter = new GpaAdapter(mLists, this);
        mListView.setAdapter(adapter);
    }

    

}
