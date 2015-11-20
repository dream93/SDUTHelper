package xyz.myhelper.sduthelper.utils;

import android.content.Context;
import android.widget.Toast;

import xyz.myhelper.sduthelper.R;

/**
 * @author dream
 * @version 1.0
 * Created by dream on 15-11-18.
 * 专用于处理吐丝的工具类
 */
public class ToastUtil {

    public  static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
