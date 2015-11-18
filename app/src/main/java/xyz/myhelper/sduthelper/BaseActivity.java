package xyz.myhelper.sduthelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import xyz.myhelper.sduthelper.ui.ToolbarHelper;

/**
 * @author dream
 * @version 1.0
 *          Created by dream on 15-11-9.
 *          所有Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        ToolbarHelper mToolbarHelper = new ToolbarHelper(this, layoutResID);
        toolbar = mToolbarHelper.getToolBar();
        setContentView(mToolbarHelper.getContentView());
        setSupportActionBar(toolbar); // 把 toolbar 设置到Activity 中
        onCreateCustomToolBar(toolbar); // 自定义的一些操作
    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
