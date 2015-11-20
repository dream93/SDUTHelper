package xyz.myhelper.sduthelper.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import xyz.myhelper.sduthelper.utils.LogUtil;

/**
 * @author dream
 * @version 1.o
 * 文件存储的工具类
 * Created by dream on 15-11-8.
 */
public class FileSave {

    public static boolean isExternalStorageReadable(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            return true;
        }else {
            return false;
        }
    }

    // 将字符串存到手机SD卡中
    public static boolean contentSavaToFlie(String content,String fileName, String filePath){
        if (isExternalStorageReadable()){
            File parentFile = new File(filePath);
            if (!parentFile.exists()){
                parentFile.mkdirs();
            }
            File file = new File(parentFile, fileName);
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                byte[] data = content.getBytes();
                bos.write(data);
                if (bos != null){
                    bos.flush();
                    bos.close();
                }
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
