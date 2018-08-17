package com.bdtt.rest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.JsonArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import net.sf.json.JSONObject;

/**
HTTPClient Put 请求处理
 */

public class HTTPPut {
 
	public static String httpput(String url,JSONObject jsonParam) throws IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(url);
		put.setHeader("Content-Type","application/json");
		put.addHeader("Accept", "application/json");
		StringEntity params =new StringEntity(jsonParam.toString(), "utf-8");
		put.setEntity(params);
		HttpResponse response = client.execute(put);
		HttpEntity httpEntity = response.getEntity();
		String result = EntityUtils.toString(httpEntity, "UTF-8");
		return result;
	}

}
