package xyz.myhelper.sduthelper;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import xyz.myhelper.sduthelper.fragment.LearnFragment;
import xyz.myhelper.sduthelper.fragment.ScheduleFragment;
import xyz.myhelper.sduthelper.helper.FragmentHelper;
import xyz.myhelper.sduthelper.utils.ToastUtil;

/**
 * @author dream
 * @version 1.0
 *  这是主类，所有一级功能都展示在这
 */
public class MainActivity extends BaseActivity {

    private Fragment fragment;
    private FragmentHelper helper;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();// 初始布局
        initFirstFragment();  // 默认进入的第一个页面

    }

    private void initView() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.bottom_bar_radioGraoup);
        radioGroup.setOnCheckedChangeListener(new RgChecked());
    }

    // RadioGroup的监听事件
    class RgChecked implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.bottom_bar_scheduleBtn:
                    fragment = new ScheduleFragment();
                    break;
                case R.id.bottom_bar_learnBtn:
                    fragment = new LearnFragment();
                    break;
            }
            helper.setTargetFragment(fragment);
            helper.setIsClearBackTask(true);
            fragmentChange(helper);
        }
    }

    private void initFirstFragment() {
        fragment = new ScheduleFragment();
        helper = new FragmentHelper();
        helper.setTargetFragment(fragment);
        helper.setIsClearBackTask(true);
        fragmentChange(helper);
    }

    // 对Fragment的统一管理
    private void fragmentChange (FragmentHelper helper){
        Fragment targetFragment = helper.getTargetFragment();
        Bundle bundle = helper.getBundle();
        boolean clearBackTask = helper.isClearBackTask();
        String fragmentTag = helper.getFragmentTag();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragmentTag != null){
            transaction.addToBackStack(fragmentTag);
        }
        if (clearBackTask){
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if (bundle != null){
            targetFragment.setArguments(bundle);
        }
        transaction.replace(R.id.main_fragment_container, targetFragment);
        transaction.commit();
    }

    // 监听后退按钮，按两次才能退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showToast(this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            }else {
                finish();
                System.exit(0); // 两种退出方式都使用
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
