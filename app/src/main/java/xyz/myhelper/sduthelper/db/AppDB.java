package xyz.myhelper.sduthelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by dream on 15-11-19.
 */
public class AppDB {
    public static final String DB_NAME = "sdut_helper"; // 数据库名
    public static final int VERSION = 1; // 数据库版本
    private static AppDB appDB;
    private SQLiteDatabase db;

    private AppDB(Context context){
        AppOpenHelper dbHelper = new AppOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static AppDB getInstance(Context context) {
        if (appDB == null){
            appDB = new AppDB(context);
        }
        return appDB;
    }

}
