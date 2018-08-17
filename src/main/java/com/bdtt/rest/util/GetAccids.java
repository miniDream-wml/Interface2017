package com.bdtt.rest.util;

import java.util.Map;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

/**
 * 根据用户ID获取accid
 * @author mouhaiyan
 *
 */

public class GetAccids {
	
	static String uid=PropertiesHandle.readValue("uid");
	
	public static String getAccidResult() {	
		String url = PropertiesHandle.readValue("imbaseUrl") + "im/getUserImInfo" + UrlAdd.UrlAdd();	
		System.out.println("url:"+url);
		Map<String, String> params = ParamsBuilt.params_built(); 
		params.put("target_uid", uid);

		String result = HTTPPost.httpPostForm(url, params);
		System.out.println(result);
        
		Gson gs = new Gson();
		TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
		JSONObject jsonObject=final_res.getData();
		String curAccid=jsonObject.getString("accid");
		
		return curAccid;
	
	}	
	public static String getTargetAccid() {	
		String url = PropertiesHandle.readValue("imbaseUrl") + "im/getUserImInfo" + UrlAdd.UrlAdd();	
		System.out.println("url:"+url);
		Map<String, String> params = ParamsBuilt.params_built(); 
		params.put("target_uid", "641291");

		String result = HTTPPost.httpPostForm(url, params);
		System.out.println(result);
        
		Gson gs = new Gson();
		TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
		JSONObject jsonObject=final_res.getData();
		String curAccid=jsonObject.getString("accid");
		
		return curAccid;
	
	}



}
