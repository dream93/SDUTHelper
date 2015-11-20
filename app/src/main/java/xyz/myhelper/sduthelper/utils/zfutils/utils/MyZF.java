package xyz.myhelper.sduthelper.utils.zfutils.utils;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author dream
 * @version 1.0
 * 单例模式，保证该类的对象共用且不能被外部实例化
 * Created by dream on 15-11-19.
 */
public class MyZF{
    private static MyZF m_instance = null;

    private DefaultHttpClient mHttpClient;
    private String myUrl;

    // 私有的构造方法，保证不被外部创建
    private MyZF(){

    }

    // 静态工厂方法，在第一次被调用时才将自己实例化
    synchronized public static MyZF getM_instance(){
        if (m_instance == null){
            m_instance = new MyZF();
        }
        return  m_instance;
    }

    public DefaultHttpClient getmHttpClient() {
        return mHttpClient;
    }

    public void setmHttpClient(DefaultHttpClient mHttpClient) {
        this.mHttpClient = mHttpClient;
    }
}
