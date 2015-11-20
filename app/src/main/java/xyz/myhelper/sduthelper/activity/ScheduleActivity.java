package xyz.myhelper.sduthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import xyz.myhelper.sduthelper.BaseActivity;
import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.adapters.ScheduleAdapter;
import xyz.myhelper.sduthelper.bean.ScheduleBean;
import xyz.myhelper.sduthelper.bean.StudentInfo;
import xyz.myhelper.sduthelper.dataparse.ScheduleParse;
import xyz.myhelper.sduthelper.utils.LogUtil;
import xyz.myhelper.sduthelper.utils.ToastUtil;

/**
 * @author dream
 * @version 1.0
 *          课表的展示页面
 *          Created by dream on 15-11-19.
 */
public class ScheduleActivity extends BaseActivity {

    List<ScheduleBean> mLists;
    private File parentPath;
    private Button exitBtn;
     private TextView nameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initComponent();
        initData();
        setSchedule();
    }

    private void initComponent() {
        exitBtn = (Button) findViewById(R.id.schedule_exitLogin);
        exitBtn.setOnClickListener(new BtnClick());
        nameTv = (TextView) findViewById(R.id.schedule_title);
    }

    private void initData() {

        // 从上个页面得到数据
        Intent intent = getIntent();
        String xh = intent.getStringExtra("xh");

        // 根据学号选择文件
        String filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "sduthelper" + File.separator + "myschedule";
        parentPath = new File(filePath + File.separator + xh + ".html");
    }

    // 退出课表
    class BtnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == exitBtn.getId()){
                finish();
            }
        }
    }

    private void setSchedule() {

        // 绑定ListView
        mLists = new ArrayList<>();
        ScheduleAdapter adapter = new ScheduleAdapter(this, mLists);
        ListView mListView = (ListView) findViewById(R.id.schedule_listview);
        mListView.setAdapter(adapter);

        // 增加ListView的头部
        View headView = LayoutInflater.from(this).inflate(R.layout.headview_listview_schedule, null);
        mListView.addHeaderView(headView);
        StudentInfo info = new StudentInfo();
        List<ScheduleBean> tempList = ScheduleParse.getSchLists(parentPath, info);
        if (tempList == null || tempList.size() == 0) {
            LogUtil.setLog(getClass(), "tempList为空，检查解析类");
            ToastUtil.showToast(this, "信息获取异常，请重新登陆");
        }else {
            mLists.addAll(tempList);

            // 把表名改为自己的名字
            String name = info.getName().substring(info.getName().indexOf("：")+1);
            nameTv.setText(name + "的课表");
        }
        adapter.notifyDataSetChanged();
    }
}


