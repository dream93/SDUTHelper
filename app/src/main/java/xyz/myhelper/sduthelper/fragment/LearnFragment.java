package xyz.myhelper.sduthelper.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import xyz.myhelper.sduthelper.R;
import xyz.myhelper.sduthelper.activity.GpaActivity;
import xyz.myhelper.sduthelper.activity.ZFJWActivity;
import xyz.myhelper.sduthelper.net.NetWorkSingleTon;
import xyz.myhelper.sduthelper.utils.FileSave;
import xyz.myhelper.sduthelper.utils.ToastUtil;
import xyz.myhelper.sduthelper.utils.zfutils.LoginZf;
import xyz.myhelper.sduthelper.utils.zfutils.net.MyHttpClientUtil;
import xyz.myhelper.sduthelper.utils.zfutils.utils.MyZF;

/**
 * @author dream
 * @version 1.o
 *          学习界面
 *          Created by dream on 15-11-20.
 */
public class LearnFragment extends Fragment {

    // RaidoGroup和ViewPager的结合
    private RadioButton gpaBtn, zfBtn;
    private ViewPager mViewPager;
    private View[] views;
    private View view;
    private final static int VIEW_SIZE = 2; // 设置ViewPager的大小，如果有新功能，可以直接从这里修改
    private View gpaLoginView;
    private View zfLoginView;

    // 绩点查询的页面
    private Button gpaLoginBtn;
    private Button gpaLoadBtn;
    private EditText xhGpaEt;
    private CheckBox rememberGpaXh;
    private SharedPreferences sharedPreferences;
    private boolean isGpaRemember;
    private String gpaXh;
    private SharedPreferences.Editor editor;
    private String filePath;
    private File parentPath;
    private Handler handler = new Handler();
    private String zfXh, zfPsw;

    // 绩点请求的参数
    public static final String GPA_POST_URL = "http://210.44.176.116/cjcx/zcjcx_list.php";
    private boolean isZfRemember;
    private Button zfLogin;
    private EditText zfXhEt;
    private EditText zfPswEt;
    private String username;
    private CheckBox rememberZf;
    private String scoreInfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn, container, false);
        initComponent();
        initView();
        initGpaView();
        initZfView();
        initData();
        return view;
    }

    private void initZfView() {
        zfLogin = (Button) zfLoginView.findViewById(R.id.learn_zf_loginBtn);
        zfLogin.setOnClickListener(new BtnClick());
        zfXhEt = (EditText) zfLoginView.findViewById(R.id.learn_zf_xh_input);
        zfPswEt = (EditText) zfLoginView.findViewById(R.id.learn_zf_psw_input);
        rememberZf = (CheckBox) zfLoginView.findViewById(R.id.learn_zf_rememberLogin);
        rememberZf.setOnCheckedChangeListener(new CBChecked());
    }

    // 登陆正方的相关方法
    private void goZfLogin() {
        zfXh = zfXhEt.getText().toString();
        zfPsw = zfPswEt.getText().toString();
        if (TextUtils.isEmpty(zfXh) || TextUtils.isEmpty(zfPsw)) {
            ToastUtil.showToast(getActivity(), "密码或账号不能为空");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginZf loginZf = new LoginZf();
                int loginState = loginZf.checkUser(zfXh, zfPsw);
                if (loginState == 1) {
                    username = loginZf.getName();
                    // 登陆成功,判断用户是否勾选了自动登陆或者记录密码
                    if (isZfRemember) { // 如果用户选择了记住密码，则将账号和密码保存
                        editor.putString("zfXh", zfXh);
                        editor.putString("zfPsw", zfPsw);
                        editor.commit();
                    }
                    goZfActivity();
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

    }

    private void goZfActivity() {
        setListViewData();
        Intent intent = new Intent(getActivity(), ZFJWActivity.class);
        intent.putExtra("scoreInfo", scoreInfo);
        startActivity(intent);
    }

    private void setListViewData() {

        // 获取成功登陆后的HttpClient实例
        DefaultHttpClient mHttpClient = MyZF.getM_instance().getmHttpClient();
        // 从网络加载数据
        scoreInfo = MyHttpClientUtil.getUrl("http://210.44.176.46/xscjcx_dq.aspx?xh=" + zfXh + "&xm=" + username
                + "&gnmkdm=" + "N121605", mHttpClient, "http://210.44.176.46/xs_main.aspx?xh=" + zfXh);
    }


    private void initData() {
        sharedPreferences = getActivity().getSharedPreferences("learn", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isGpaRemember = sharedPreferences.getBoolean("isGpaRemember", false);
        isZfRemember = sharedPreferences.getBoolean("isZfRemember", false);
        if (isGpaRemember) {
            rememberGpaXh.setChecked(true);
            setGpaInput();
        }

    }

    private void setZfInput() {
        zfXh = sharedPreferences.getString("zfXh", "");
        zfPsw = sharedPreferences.getString("zfPsw", "");
        zfXhEt.setText(zfXh);
        zfPswEt.setText(zfPsw);
    }

    private void setGpaInput() {
        gpaXh = sharedPreferences.getString("gpaXh", "");
        xhGpaEt.setText(gpaXh);
    }

    private void initComponent() {

        gpaBtn = (RadioButton) view.findViewById(R.id.learn_gpa_choice);
        zfBtn = (RadioButton) view.findViewById(R.id.learn_zf_choice);

        RadioGroup choiceGroup = (RadioGroup) view.findViewById(R.id.learn_choice_group);
        choiceGroup.setOnCheckedChangeListener(new RadioGroupChange());
        mViewPager = (ViewPager) view.findViewById(R.id.learn_viewPager);
        views = new View[VIEW_SIZE];

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPagerChange());
    }


    // 初始化组件
    private void initView() {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        gpaLoginView = mInflater.inflate(R.layout.leanrn_login_gpa, null);
        zfLoginView = mInflater.inflate(R.layout.learn_login_zf, null);
        views[0] = gpaLoginView;
        views[1] = zfLoginView;
    }

    // 初始化绩点登陆页面组件
    private void initGpaView() {
        gpaLoginBtn = (Button) gpaLoginView.findViewById(R.id.leanrn_login_gap_loginBtn);
        gpaLoginBtn.setOnClickListener(new BtnClick());
        gpaLoadBtn = (Button) gpaLoginView.findViewById(R.id.learn_login_gpa_loadBtn);
        gpaLoadBtn.setOnClickListener(new BtnClick());
        rememberGpaXh = (CheckBox) gpaLoginView.findViewById(R.id.learn_login_gpa_rememberLogin);
        rememberGpaXh.setOnCheckedChangeListener(new CBChecked());
        xhGpaEt = (EditText) gpaLoginView.findViewById(R.id.learn_login_gpa_input);
    }

    // 按钮的监听事件
    class BtnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.leanrn_login_gap_loginBtn:
                    goGpaLogin();
                    break;
                case R.id.learn_login_gpa_loadBtn:
                    goGpaLoad();
                    break;
                case R.id.learn_zf_loginBtn:
                    goZfLogin();
                    break;
            }
        }
    }

    private void goGpaLoad() {
        if (isGpaXhEmpty()) {
            ToastUtil.showToast(getActivity(), "学号不能为空");
            return;
        }
        if (!parentPath.exists()) {
            ToastUtil.showToast(getActivity(), "本地文件不存在，请选择在线方式");
        } else {
            goGpaActivity();
        }
    }


    private void goGpaLogin() {
        if (isGpaXhEmpty()) {
            ToastUtil.showToast(getActivity(), "学号不能为空");
            return;
        }
        final String post_xuehao = gpaXh;
        // 从网络加载数据
        RequestQueue requestQueue = NetWorkSingleTon.getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GPA_POST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s.contains("没有该学号(" + post_xuehao + ")的基本信息。")) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(getActivity(), "没有该学号的信息");
                                }
                            });
                        } else {
                            if (parentPath.exists()) {
                                parentPath.delete();
                            }
                            FileSave.contentSavaToFlie(s, post_xuehao + ".html", filePath);
                            goGpaActivity();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(getActivity(), "请检查网络后重试");
                    }
                });
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("post_xuehao", post_xuehao);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void goGpaActivity() {
        if (rememberGpaXh.isChecked()) {
            editor.putString("gpaXh", gpaXh);
            editor.commit();
        }
        Intent intent = new Intent(getActivity(), GpaActivity.class);
        intent.putExtra("xh", gpaXh);
        startActivity(intent);
    }


    private boolean isGpaXhEmpty() {
        gpaXh = xhGpaEt.getText().toString();
        filePath = Environment.getExternalStorageDirectory().getPath() + File.separator
                + "sduthelper" + File.separator + "mygpa";
        parentPath = new File(filePath + File.separator + gpaXh + ".html");
        return gpaXh == null || "".equals(gpaXh);
    }

    // RadioGroup的监听，按下后更换页面
    class RadioGroupChange implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {

                // 切换选项卡
                case R.id.learn_gpa_choice:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.learn_zf_choice:
                    mViewPager.setCurrentItem(1);
                    break;
                default:
                    break;
            }
        }
    }

    class CBChecked implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView == rememberGpaXh) {
                if (isChecked) {
                    editor.putBoolean("isGpaRemember", true);
                } else {
                    editor.putBoolean("isGpaRemember", false);
                }
                editor.commit();
            }
            if (buttonView == rememberZf) {
                if (isChecked) {
                    editor.putBoolean("isZfRemember", true);
                } else {
                    editor.putBoolean("isZfRemember", false);
                }
                editor.commit();
            }
        }
    }

    // ViewPager切换时，RadioGroup同时更换
    class ViewPagerChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                gpaBtn.setBackgroundResource(R.drawable.radio_button_left_select_true);
                gpaBtn.setTextColor(0xffffffff);
                zfBtn.setBackgroundResource(R.drawable.radio_button_right_select_false);
                zfBtn.setTextColor(0xff0080ff);
            } else if (position == 1) {
                if (isZfRemember) {
                    rememberZf.setChecked(true);
                    setZfInput();
                }
                gpaBtn.setBackgroundResource(R.drawable.radio_button_left_select_false);
                gpaBtn.setTextColor(0xff0080ff);
                zfBtn.setBackgroundResource(R.drawable.radio_button_right_select_true);
                zfBtn.setTextColor(0xffffffff);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    // 给ViewPager设置适配器
    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views[position]);
            return views[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views[position]);
        }
    }

}
