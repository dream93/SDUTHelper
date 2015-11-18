package xyz.myhelper.sduthelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import xyz.myhelper.sduthelper.utils.VersionCheck;

/**
 * @author dream
 *         Created by dream on 15-11-16.
 *         应用的欢迎页面
 *         处理用户是否为第一次进入或更新应用
 * @version 1.0
 */
public class WelcomActivity extends AppCompatActivity {

    private ImageView shanIv, liIv, zhuIv, shouIv;

    // 字的补间动画
    private TranslateAnimation shanAnimation, liAnimation, zhuAnimation, shouAnimation;

    // 设置水平位移和垂直位移的时间
    private final static int HORIZONTAL_DURING_TIME = 200;
    private final static int VERTICAL_DURING_TIME = 800;

    // 标记位
    private boolean isFirst;

    // 存储的版本号
    private String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        initImg(); // 找到图片资源
        initAnim(); // 启动应用的补间动画
    }

    private void initImg() {
        shanIv = (ImageView) findViewById(R.id.welcom_shan);
        liIv = (ImageView) findViewById(R.id.welcom_li);
        zhuIv = (ImageView) findViewById(R.id.welcom_zhu);
        shouIv = (ImageView) findViewById(R.id.welcom_shou);
    }

    private void initAnim() {

        // “山”字的动画效果
        shanAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_PARENT, 0.1f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        shanAnimation.setDuration(HORIZONTAL_DURING_TIME);
        shanAnimation.setFillAfter(true);
        shanIv.setVisibility(View.VISIBLE);
        shanIv.setAnimation(shanAnimation);
        shanAnimation.setAnimationListener(new ImgAnimListener());// 有四个补间动画，因此设置内部类进行监听

        // “理”字的动画效果
        liAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_PARENT, 0.6f);
        liAnimation.setDuration(VERTICAL_DURING_TIME);
        liAnimation.setFillAfter(true);
        liAnimation.setAnimationListener(new ImgAnimListener());

        // “助”字的动画效果
        zhuAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_PARENT, -0.3f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        zhuAnimation.setDuration(HORIZONTAL_DURING_TIME);
        zhuAnimation.setFillAfter(true);
        zhuAnimation.setAnimationListener(new ImgAnimListener());

        // “手”字的动画效果
        shouAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_PARENT, -0.33f);
        shouAnimation.setDuration(VERTICAL_DURING_TIME);
        shouAnimation.setFillAfter(true);
        shouAnimation.setAnimationListener(new ImgAnimListener());
    }

    class ImgAnimListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

            // 在动画开始前，进行程序必要的初始化操作
            if (animation == shanAnimation) {
                SharedPreferences pref = getSharedPreferences("first", MODE_PRIVATE);
                isFirst = pref.getBoolean("isFirst", true); // 得到存储中的进入状态
                version = pref.getString("version", "1.0"); // 得到存储中的版本号
                Log.i("MyTag", version);
            }


        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == shanAnimation) {
                liIv.setVisibility(View.VISIBLE);
                liIv.startAnimation(liAnimation);
            } else if (animation == liAnimation) {
                zhuIv.setVisibility(View.VISIBLE);
                zhuIv.startAnimation(zhuAnimation);
            } else if (animation == zhuAnimation) {
                shouIv.setVisibility(View.VISIBLE);
                shouIv.startAnimation(shouAnimation);
            } else if (animation == shouAnimation) {
                if (isFirst) { // 第一次进入，则跳转到向导界面
                    Log.i("MyTag", " " + isFirst);
                    goToGuide();
                } else { // 否则检查版本
                    if (version.equals(VersionCheck.getVersionName(WelcomActivity.this))) {

                        // 版本号一致，跳转主页面
                        goToMain();
                    } else { // 版本号不一致，说明有更新或回退，进入向导页
                        goToGuide();
                    }

                }
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private void goToGuide() {
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
        finish(); // 进入后不用再返回此页面
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
