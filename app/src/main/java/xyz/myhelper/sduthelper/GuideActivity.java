package xyz.myhelper.sduthelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import xyz.myhelper.sduthelper.utils.VersionCheck;

/**
 * @author dream
 * @version 1.0
 *          Created by dream on 15-11-17.
 *          首次进入应用或更新应用后的向导页面
 */
public class GuideActivity extends AppCompatActivity {

    private Button mButton;
    private final static int VIEW_SIZE = 3; // 设置向导页的页数
    private View[] pageViews; // 设置页面的内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initComponent();
    }


    // 初始化向导页面的组件，并加入到ViewPager中
    private void initComponent() {

        pageViews = new View[VIEW_SIZE];


        mButton = (Button) findViewById(R.id.guid_button);
        mButton.setOnClickListener(new BtnClick());

        View view1 = new ImageView(this);
        view1.setBackgroundResource(R.mipmap.guide_1);
        pageViews[0] = view1;
        View view2 = new ImageView(this);
        view2.setBackgroundResource(R.mipmap.guide_2);
        pageViews[1] = view2;
        View view3 = new ImageView(this);
        view3.setBackgroundResource(R.mipmap.guide_3);
        pageViews[2] = view3;

        // 设置适配器
        ViewPager mViewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        MyPagerAdapter adapter = new MyPagerAdapter();
        mViewPager.setAdapter(adapter);

        // 设置监听
        mViewPager.addOnPageChangeListener(new MyPageChange());

    }

    // ViewPager的监听方法
    class MyPageChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            // 如果页面到了最后一页，则将按钮显示，否则隐藏
            if (arg0 == (pageViews.length - 1)) {
                mButton.setVisibility(View.VISIBLE);
            } else {
                mButton.setVisibility(View.GONE);
            }
        }

    }

    // 按钮的监听事件
    class BtnClick implements android.view.View.OnClickListener {

        @Override
        public void onClick(View v) {

            // 按下按钮，代表已经进入，置为true
            SharedPreferences pref = getSharedPreferences("first", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", false);
            editor.putString("version", VersionCheck.getVersionName(GuideActivity.this));
            editor.apply(); // 需要提交才能生效
            Log.i("MyTag", VersionCheck.getVersionName(GuideActivity.this));
            // 跳转到主页面
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // 进入后按返回按钮不再进入向导页面
        }
    }

    // PagerAdapter
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageViews[position]);
            return pageViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pageViews[position]);
        }
    }
}
