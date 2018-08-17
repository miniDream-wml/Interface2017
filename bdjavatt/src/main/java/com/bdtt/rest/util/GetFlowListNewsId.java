package com.bdtt.rest.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Ryan Tang
 * 通过调用flow/list接口来获取资讯-推荐页面的newsID
 *
 */

public class GetFlowListNewsId  {
	private static String testUrl="flow/list";
	private static String baseUrl=PropertiesHandle.readValue("newsbaseUrl");
	private static String channel_id = "1"; //1-推荐， 2-本地， 其他订阅的tag
	private static String mode = "1"; //1 - 自动刷新， 2 - 下拉 3 - 上拉
	private static String last_time = ""; //刷新时间小于20分钟时返回空
	private static String offset = "0"; //当前已展示资讯数
	private static String ad_offset = "0"; //当前频道已刷新广告数
	private static String score = ""; //在本地频道，需要传，其他传空就好
	
	public static String getNewsID(int i)  {	
		String url = baseUrl + testUrl + UrlAdd.UrlAdd();
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("channel_id", channel_id);
        params.put("mode", mode);
        params.put("last_time", last_time);
        params.put("offset", offset);
        params.put("ad_offset", ad_offset);
        params.put("score", score);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        JSONObject data = final_res.getData();
		JSONArray newslist = data.getJSONArray("items"); 
		JSONObject news = newslist.getJSONObject(i);
		String newsID = news.getString("news_id");
		System.out.println("资讯ID："+newsID);
		return newsID;
		
	}	
	public static void main (String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		GetFlowListNewsId.getNewsID(1);
	}
	
}

