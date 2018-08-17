package com.bdtt.rest.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author mouhaiyan
 * 获取本地资讯第一篇文章的newsID
 *
 */

public class GetNewsId  {
	static String testUrl="index/index";
	static String baseUrl=PropertiesHandle.readValue("newsbaseUrl");
	
	public static String getNewsID()  {	
		String url = baseUrl + testUrl + UrlAdd.UrlAdd();
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("index", "1");
        params.put("mode", "1");

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        JSONObject res=final_res.getData();
		JSONArray arts=res.getJSONArray("card_list"); 
		JSONArray  localart=arts.getJSONObject(0).getJSONArray("items");//本地资讯
		JSONObject localObj=localart.getJSONObject(0);
		String newsID=localObj.getString("news_id");
		System.out.println("资讯ID："+newsID);
		return newsID;
		
	}	
	public static void main (String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		GetNewsId.getNewsID();
	}
	
}

