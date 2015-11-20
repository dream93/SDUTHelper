package xyz.myhelper.sduthelper.helper;

import android.app.Application;

import xyz.myhelper.sduthelper.net.NetWorkSingleTon;

/**
 * 全局类
 * 实例化Volley
 * Created by dream on 15-11-20.
 */
public class MainApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkSingleTon.createNetWorkSingleTon(getApplicationContext());
    }
}
