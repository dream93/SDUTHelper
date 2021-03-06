package xyz.myhelper.sduthelper.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import xyz.myhelper.sduthelper.R;

/**
 * @author dream
 * @version 1.0
 * Created by dream on 15-11-14.
 */
public class ToolbarHelper {

    private Context mContext; // 创建View时要用到的上下文
    private FrameLayout mContentView;// 基础的View
    private Toolbar mToolbar; // 每个Activity要用到的Toolbar控件
    private LayoutInflater mInflater; // 视图构造器

    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    public ToolbarHelper(Context context, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);

        // 初始化整个内容
        initContentView();

        // 初始化用户定义的布局
        initUserView(layoutId);

        // 初始化toolbar
        initToolBar();
    }

    private void initContentView() {

        // 直接创建一个帧布局，作为视图容器的父容器
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);

    }

    private void initToolBar() {

        // 通过inflater获取toolbar的布局文件
        View toolbar = mInflater.inflate(R.layout.ui_toolbar, mContentView);
        mToolbar = (Toolbar) toolbar.findViewById(R.id.ui_tool_bar);
    }

    private void initUserView(int id) {
        View mUserView = mInflater.inflate(id, null); // 用户的视图
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);

        // 获取主题中定义的悬浮标志
        boolean overly = typedArray.getBoolean(0, false);

        //获取主题中定义的toolbar的高度
        int toolBarSize = (int) typedArray.getDimension(1, (int) mContext.getResources().getDimension(R.dimen.toolbar_default_height_material));
        typedArray.recycle();

        // 如果是悬浮状态，则不需要设置间距
        params.topMargin = overly ? 0 : toolBarSize;
        mContentView.addView(mUserView, params);

    }

    public FrameLayout getContentView() {
        return mContentView;
    }

    public Toolbar getToolBar() {
        return mToolbar;
    }
}