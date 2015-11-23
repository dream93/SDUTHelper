package xyz.myhelper.sduthelper.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;

import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.activity.ScheduleActivity;
import xyz.myhelper.sduthelper.net.NetWorkState;
import xyz.myhelper.sduthelper.utils.FileSave;
import xyz.myhelper.sduthelper.utils.ToastUtil;
import xyz.myhelper.sduthelper.utils.zfutils.LoginZf;
import xyz.myhelper.sduthelper.utils.zfutils.net.MyHttpClientUtil;
import xyz.myhelper.sduthelper.utils.zfutils.utils.MyZF;

/**
 * @author dream
 * @version 1.0
 *          Created by dream on 15-11-18.
 *          课表的登陆界面
 */
public class ScheduleFragment extends Fragment {

    private View view;
    private EditText xhEt, pswEt;//学号和密码的输入框
    private CheckBox autoCB, remeberCB; // 自动登陆和记住密码

    private boolean isAuto, isRemeber; // 与登陆相对应的状态
    private boolean loginType; // 登陆方式

    private String xh, psw;
    private String username;
    private SharedPreferences sharedPreferences;

    private int loginState; // 记录登陆成功与失败的状态

    // 存储相关
    private String filePath; // 存储的路径
    private File parentPath; // 文件

    private Handler handler;
    private SharedPreferences.Editor editor;

    // 请求课表数据的网址
    public static final String POST_PATH = "http://210.44.176.46/xskbcx.aspx?xh=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        initComponent(); // 找到控件
        initData();  // 初始化数据
        initState(); // 判断状态，选择登陆方式
        return view;
    }

    private void initState() {
        if (isAuto) { // 先判断用户是否选择了自动登陆
            editor.putBoolean("isAuto", true);
            editor.commit();
            // 用户上次登陆时选择了自动登陆，直接跳转到课表展示页面
            getSPText(); // 得到存储的数据
            getDataFromLocal(); // 自动登陆默认从本地加载

        } else if (isRemeber) { // 判断用户上次登陆是否选择了记住密码
            remeberCB.setChecked(true);

            // 用户记住了密码，则将保存的密码恢复到输入框
            getSPText();
            xhEt.setText(xh);
            pswEt.setText(psw);
        }
    }

    private void getInputText() {
        xh = xhEt.getText().toString();
        psw = pswEt.getText().toString();
    }


    public boolean isXhOrPswIsEmpty() {
        // 判断输入的学号和密码是否为空
        return TextUtils.isEmpty(xh) || TextUtils.isEmpty(psw);
    }

    // 设置按钮的监听
    class BtnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_login_loginBtn:
                    loginType = true;
                    // 执行登陆
                    getInputText();
                    goToLogin();
                    break;
                case R.id.layout_login_loadBtn:
                    loginType = false;
                    // 执行登陆
                    getInputText();
                    goToLogin();
                    break;
            }
        }
    }

    private void goToLogin() {
        if (isXhOrPswIsEmpty()) {
            ToastUtil.showToast(getActivity(), "学号或账号不能为空");
            return;
        }
        if (loginType) { // 选择联网登陆
            if (!NetWorkState.isNetWorkState(getActivity())) {
                ToastUtil.showToast(getActivity(), "请检查网络是否联通");
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LoginZf loginZf = new LoginZf();
                    loginState = loginZf.checkUser(xh, psw);
                    if (loginState == 1) {
                        username = loginZf.getName();
                        // 登陆成功,判断用户是否勾选了自动登陆或者记录密码
                        getDataFromNet();
                        if (isRemeber) { // 如果用户选择了记住密码，则将账号和密码保存
                            editor.putString("xh", xh);
                            editor.putString("psw", psw);
                            editor.commit();
                        }
                        goSheduleActivity();
                    } else { // 返回了错误值，提示用户
                        String notice;
                        if (loginState == 0) {
                            notice = "请检查账号或密码";
                        } else if (loginState == -1) {
                            notice = "暂无法与服务器连接，稍后再试";
                        } else if (loginState == -2) {
                            notice = "登陆失败， 系统正忙";
                        } else {
                            notice = "未知错误";
                        }
                        final String finalNotice = notice;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(getActivity(), finalNotice);
                            }
                        });
                    }
                }
            }).start();
        } else {
            // 离线加载
            getDataFromLocal();
        }
    }


    private String getDataFromNet() { // 从网络获取数据

        // 获取成功登陆后的HttpClient实例
        DefaultHttpClient mHttpClient = MyZF.getM_instance().getmHttpClient();

        String scheInfo = MyHttpClientUtil.getUrl(POST_PATH + xh + "&xm=" + username
                + "&gnmkdm=" + "N121603", mHttpClient, POST_PATH + xh);

        // 将数据存入到文件中
        parentPath = new File(filePath + File.separator + xh + ".html");
        if (parentPath.exists()) {
            parentPath.delete();
        }
        FileSave.contentSavaToFlie(scheInfo, xh + ".html", filePath);
        return scheInfo;
    }

    // 从本地获取数据
    private void getDataFromLocal() {

        parentPath = new File(filePath + File.separator + xh + ".html");
        if (parentPath.exists()) {

            // 从本地文件中获取课程表
            goSheduleActivity();
        } else {

            // 用户可能已经删除了该文件，选择在线查询
            loginType = true;
            goToLogin();
        }
    }

    // 设置多选框的监听
    class BoxChecked implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            // 记录用户的选择，是否选择自动登陆或者记住密码
            switch (buttonView.getId()) {
                case R.id.layout_login_autoLogin:
                    if (isChecked) { // 如果选中了自动登陆，则登陆成功后记录学号和密码
                        remeberCB.setChecked(true); // 自动登陆则必定记住密码
                        editor.putBoolean("isAuto", true); // 将自动登陆的标志位置true
                        isAuto = true;
                        isRemeber = true;
                    } else {
                        editor.putBoolean("isAuto", false); // 将自动登陆的标志位置false
                        isAuto = false;
                    }
                    editor.commit();
                    break;

                case R.id.layout_login_rememberLogin:
                    if (isChecked) { // 是否记住密码
                        editor.putBoolean("isRemember", true);
                        isRemeber = true;
                        // 当登陆成功，才记录密码
                    } else {
                        editor.putBoolean("isRemember", false);
                        autoCB.setChecked(false);
                        isRemeber = false;
                    }
                    editor.commit();
                    break;
            }

        }
    }

    // 跳转到课表展示页面,传递
    private void goSheduleActivity() {
        Intent intent = new Intent(getActivity(), ScheduleActivity.class);
        intent.putExtra("xh", xh);// 将解析交给课表展示页进行
        startActivity(intent);
    }

    private void getSPText() {

        // 用户选择自动登陆或保存密码后的信息
        xh = sharedPreferences.getString("xh", ""); // 学号
        psw = sharedPreferences.getString("psw", ""); // 密码
    }

    private void initData() {
        filePath = Environment.getExternalStorageDirectory().getPath() + File.separator +
                "sduthelper" + File.separator + "myschedule"; // 课表的所有数据保存目录

        // 用户上次登陆的状态记录
        sharedPreferences = getActivity().getSharedPreferences("scheduleState",
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        isAuto = sharedPreferences.getBoolean("isAuto", false); // 是否自动登陆
        isRemeber = sharedPreferences.getBoolean("isRemember", false); // 是否保存了密码

        // 实例化handler
        handler = new Handler();
    }

    // 找到按钮设置监听
    private void initComponent() {
        xhEt = (EditText) view.findViewById(R.id.layout_login_xh_input);
        pswEt = (EditText) view.findViewById(R.id.layout_login_psw_input);
        autoCB = (CheckBox) view.findViewById(R.id.layout_login_autoLogin);
        autoCB.setOnCheckedChangeListener(new BoxChecked());
        remeberCB = (CheckBox) view.findViewById(R.id.layout_login_rememberLogin);
        remeberCB.setOnCheckedChangeListener(new BoxChecked());
        Button loginBtn = (Button) view.findViewById(R.id.layout_login_loginBtn);
        loginBtn.setOnClickListener(new BtnClick());
        Button loadBtn = (Button) view.findViewById(R.id.layout_login_loadBtn);
        loadBtn.setOnClickListener(new BtnClick());
    }
}
