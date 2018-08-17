package com.bdtt.rest.util;

import com.google.gson.Gson;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by tangqiliang on 2017/8/9.
 * Modified by tangqliang on 2017/11/24
 * 通过调用“获取本地圈动态”接口来获取第i条动态的feed_id, i从0开始，0表示第一条，1表示第二条，依此类推
 */

public class GetCircleFeedId {
	
	static String testUrl="Circle/getFeedByChannel";
	static String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	
	public static String getCircleFeedId (int i) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
    	String channel_id = "0";
    	String index = "1";
    	String mode = "1";
        //System.out.println(url);
       
    	Map<String, String> parames = ParamsBuilt.params_built(); 
        parames.put("channel_id", channel_id);
        parames.put("index", index);
        parames.put("mode", mode);

        System.out.println(parames);
        String result = HTTPPost.httpPostForm(url, parames);
        System.out.println(result);
                
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        
        JSONArray feed_list = (JSONArray) dataResult.get("feed_list");
        JSONObject feed = feed_list.getJSONObject(i);
        String feed_id = feed.get("feed_id").toString();
        System.out.println(feed_id);
        
        return feed_id;
	}	
	
	public static String getCircleFeedUid (int i) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
    	String channel_id = "0";
    	String index = "1";
    	String mode = "1";
        //System.out.println(url);
       
    	Map<String, String> parames = ParamsBuilt.params_built(); 
        parames.put("channel_id", channel_id);
        parames.put("index", index);
        parames.put("mode", mode);

        System.out.println(parames);
        String result = HTTPPost.httpPostForm(url, parames);
        System.out.println(result);
                
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        
        JSONArray feed_list = (JSONArray) dataResult.get("feed_list");
        JSONObject feed = feed_list.getJSONObject(i);
        String uid = feed.get("uid").toString();
        System.out.println(uid);
        
        return uid;
	}
	
	public static void main (String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		GetCircleFeedId.getCircleFeedId(3);
		GetCircleFeedId.getCircleFeedId(4);
		GetCircleFeedId.getCircleFeedUid(3);
		GetCircleFeedId.getCircleFeedUid(4);
		//GetCircleFeedId.getTheThirdCircleFeedId();
	}
}


