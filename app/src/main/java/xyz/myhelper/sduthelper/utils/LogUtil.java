package xyz.myhelper.sduthelper.utils;

import android.content.Context;
import android.util.Log;

/**
 * 控制Log日志打印工具类
 * Created by dream on 15-11-19.
 */
public class LogUtil {

    private final static boolean flag = false;

    public static void setLog(Class clazz, String logConent){
        if (flag){
            Log.i("MyTag", clazz.getName() + logConent);
        }

    }

    public static void setNoClassLog(String logContent){
        if (flag){
            Log.i("MyTag", logContent);
        }
    }
}
