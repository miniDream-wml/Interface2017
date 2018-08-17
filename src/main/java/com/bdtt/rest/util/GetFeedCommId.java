package com.bdtt.rest.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author mouhaiyan
 * 获取西藏－拉萨－林周本地圈第一条动态的第一条评论的ID
 *
 */

public class GetFeedCommId {
	
	static String feedlistUrl="Circle/getFeedByChannel";
	static String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	static String region_id=PropertiesHandle.readValue("region_id");
	
	public static String getFeedCommId () throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + feedlistUrl + UrlAdd.UrlAdd();
    	String channel_id = "0";
    	String index = "1";
    	String mode = "1";
    	//String region_id="401011";
       
    	Map<String, String> parames = ParamsBuilt.params_built(); 
        parames.put("channel_id", channel_id);
        parames.put("index", index);
        parames.put("mode", mode);
        parames.put("region_id", region_id);

        System.out.println(parames);
        String result = HTTPPost.httpPostForm(url, parames);
        System.out.println(result);
                
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        
        JSONArray feed_list = (JSONArray) dataResult.get("feed_list");
        JSONObject feed = feed_list.getJSONObject(0);
        JSONArray comm_list= (JSONArray) feed.get("comments");
        JSONObject commObj=comm_list.getJSONObject(0);
        String comm_id=commObj.getString("comment_id");
        System.out.println(comm_id);
        
        return comm_id;
	}	
	
	public static String getFeedCommUid () throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + feedlistUrl + UrlAdd.UrlAdd();
    	String channel_id = "0";
    	String index = "1";
    	String mode = "1";
    	//String region_id="401011";
       
    	Map<String, String> parames = ParamsBuilt.params_built(); 
        parames.put("channel_id", channel_id);
        parames.put("index", index);
        parames.put("mode", mode);
        parames.put("region_id", region_id);

        System.out.println(parames);
        String result = HTTPPost.httpPostForm(url, parames);
        System.out.println(result);
                
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        
        JSONArray feed_list = (JSONArray) dataResult.get("feed_list");
        JSONObject feed = feed_list.getJSONObject(0);
        JSONArray comm_list= (JSONArray) feed.get("comments");
        JSONObject commObj=comm_list.getJSONObject(0);
        String uid=commObj.getString("uid");
        System.out.println(uid);
        
        return uid;
	}
	
	public static void main (String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		GetFeedCommId.getFeedCommId();
		GetFeedCommId.getFeedCommUid();
	}
}
