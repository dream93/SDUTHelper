package xyz.myhelper.sduthelper.utils.zfutils.net;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by dream on 15-11-19.
 * HttpClient的联网类
 */
public class MyHttpClientUtil {

    public static String getUrl(String url, DefaultHttpClient httpClient, String setHeader) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Referer", setHeader);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String postUrl(String url, List<BasicNameValuePair> pairs,
                                 DefaultHttpClient httpClient, String setHeader)
            throws ClientProtocolException, IOException {

        HttpPost request = new HttpPost(url);
        request.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
        request.setHeader("Referer", setHeader);
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                10000); // 读取超时

        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        } else {
            return null;
        }

    }

}
