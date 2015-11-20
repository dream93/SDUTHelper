package xyz.myhelper.sduthelper.net;

import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import xyz.myhelper.sduthelper.utils.LogUtil;

/**
 * Created by dream on 15-11-8.
 */
public class NetWorkState {

   public static boolean isNetWorkState(Context context){
       ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo info = manager.getActiveNetworkInfo();
       if (info != null){
           return true;
       }
       return false;
   }
}
