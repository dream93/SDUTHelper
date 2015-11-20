package xyz.myhelper.sduthelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库工具类
 * 建立三张表：学生，课表，绩点
 * Created by dream on 15-11-19.
 */
public class AppOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table User("
            + "id integer primary key autoincrement, "
            + "user_xh text, "
            + "user_psw text)";
    public static final String CREATE_SCHEDULE = "create table SCHEDULE("
            + "id integer primary key autoincrement, "
            + "sche_name text, "
            + "sche_place text, "
            + "sche_tech text, "
            + "user_id integer)";
    public static final String CREATE_GPA = "create table MYGPA("
            + "id integer primary key autoincrement,"
            + "gpa_subject text, "
            + "gpa_grade text, "
            + "gpa_score text, "
            + "gpa_new text, "
            + "user_id integer)";

    public AppOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version){
        super(context, name, factory, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_SCHEDULE);
        db.execSQL(CREATE_GPA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
