package xyz.myhelper.sduthelper.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @author dream
 *         Created by dream on 15-11-17.
 *         版本检查
 * @version 1.0
 */
public class VersionCheck {

    // 获取当前版本号
    public static String getVersionName(Context context) {

        PackageManager packageManager = context.getPackageManager(); // 获取PackageManager的版本号
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
