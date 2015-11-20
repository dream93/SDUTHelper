package xyz.myhelper.sduthelper.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Volley网络管理类
 * Created by dream on 15-11-20.
 */
public class NetWorkSingleTon {
    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    //RequestQueue需要
    private Context context;

    private static NetWorkSingleTon instance;

    //公有的创造方法
    public static void createNetWorkSingleTon(Context context) {
        if (instance == null) {
            instance = new NetWorkSingleTon(context);
        }
    }


    //私有的构造方法
    private NetWorkSingleTon(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            //获取运行内存的八分之一
            int size = (int) (Runtime.getRuntime().totalMemory()) / 8;

            private LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(size) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    int ret = value.getRowBytes() * value.getHeight();
                    return ret;
                }
            };

            @Override
            public Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url, bitmap);
            }
        });
    }


    /**
     * 公有的访问方法
     *
     * @return
     */
    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }
}

