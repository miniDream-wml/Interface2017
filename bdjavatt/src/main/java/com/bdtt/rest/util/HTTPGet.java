package com.bdtt.rest.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 此函数的作用是输入一个url会返回一个json格式
 */
public class HTTPGet {
    public static String httpGet(String url) {
        HttpClient hc = new DefaultHttpClient();
        HttpGet hg = new HttpGet(url);;
        try {
            HttpResponse response = hc.execute(hg);
            HttpEntity entity = response.getEntity();
            String result = null;
            if(entity != null) {
                result = EntityUtils.toString(entity,"UTF-8");
            }
            return result;
        }
        catch(ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        hc.getConnectionManager().shutdown();
        return null;
    }
}
