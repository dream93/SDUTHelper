package xyz.myhelper.sduthelper.utils.zfutils;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import xyz.myhelper.sduthelper.utils.zfutils.net.MyHttpClientUtil;
import xyz.myhelper.sduthelper.utils.zfutils.utils.MyZF;
import xyz.myhelper.sduthelper.utils.zfutils.utils.StringUtil;

/**
 * @author dream
 * @version 1.0
 *          正方教务系统的登陆页面
 *          Created by dream on 15-11-19.
 */
public class LoginZf {
    public static final String post_url = "http://210.44.176.46/default2.aspx"; // 外网登陆地址
    private String name;

    // 用户登陆返回的状态信息
    private final static int SUCCESS = 1, USERERROR = 0, SYSERROR = 2, NETERROR = -1;

    // 检查用户登陆是否正确
    public int checkUser(String username, String psw) {

        // 得到mHttpClient的实例
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        // 请求超时
        mHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        // 读取超时
        mHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

        // 此处先获取页面，读取到value值为post做准备
        String __VIEWSTATE = "";
        String temp = "";
        StringTokenizer tokenizer = null;
        temp = MyHttpClientUtil.getUrl(post_url, mHttpClient, "");
        if (temp == "") {
            return -1;
        }
        tokenizer = new StringTokenizer(temp);
        while (tokenizer.hasMoreTokens()) {
            String valueToken = tokenizer.nextToken();
            if (StringUtil.isValue(valueToken, "value")) {
                if (StringUtil.getValue(valueToken, "value", "\"", 7).length() == 48) {
                    __VIEWSTATE = StringUtil.getValue(valueToken, "value", "\"", 7);// value
                }
            }
        }

        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
        pairs.add(new BasicNameValuePair("txtUserName", username));
        pairs.add(new BasicNameValuePair("TextBox2", psw));
        pairs.add(new BasicNameValuePair("RadioButtonList1", "%D1%A7%C9%FA"));
        pairs.add(new BasicNameValuePair("Button1", null));
        pairs.add(new BasicNameValuePair("lbLanguage", null));

        String info = "";
        try {
            info = MyHttpClientUtil.postUrl(post_url, pairs, mHttpClient, "http://210.44.176.46/");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (info != "") {
            MyZF.getM_instance().setmHttpClient(mHttpClient);
            tokenizer = new StringTokenizer(info);
            while (tokenizer.hasMoreTokens()) {
                String valueToken = tokenizer.nextToken();
                if (StringUtil.isValue(valueToken, "defer")) {
                    return USERERROR;
                }
                if (StringUtil.isValue(valueToken, " <title>ERROR")) {
                    return SYSERROR;
                }
                if (StringUtil.isValue(valueToken, "id=\"xhxm")) {
                    name = valueToken.toString().substring(valueToken.toString().indexOf(">") + 1,
                            valueToken.toString().indexOf("同")); // 得到登陆人的名字
                    return SUCCESS;
                }
            }

        }
        return NETERROR;
    }

    public String getName() {
        return name;
    }
}