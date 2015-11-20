package xyz.myhelper.sduthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import java.io.File;
import java.util.List;

import xyz.myhelper.sduthelper.BaseActivity;
import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.adapters.GpaAdapter;
import xyz.myhelper.sduthelper.bean.GpaBean;
import xyz.myhelper.sduthelper.bean.StudentInfo;
import xyz.myhelper.sduthelper.dataparse.GpaParse;

public class GpaActivity extends BaseActivity {

    // ListView需要的组件
    private ListView mListView;
    private List<GpaBean> mLists;
    private GpaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
        initData();
    }

    private void initData() {
        mListView = (ListView) findViewById(R.id.gpa_show_list);
        Intent intent = getIntent();
        String xh = intent.getStringExtra("xh");
        String filePath = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "sduthelper" + File.separator + "mygpa";
        File parentPath = new File(filePath + File.separator + xh + ".html");
        StudentInfo info  = new StudentInfo();
        List<GpaBean> mLists = GpaParse.getGPAList(parentPath, info);
        adapter = new GpaAdapter(mLists, this);
        mListView.setAdapter(adapter);
    }

}
