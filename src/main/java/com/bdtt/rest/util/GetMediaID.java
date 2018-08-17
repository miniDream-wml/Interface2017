package com.bdtt.rest.util;
/**
 * 获取站点媒体公众号和频道ID
 * @author mouhaiyan
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetMediaID {
	static String uid=PropertiesHandle.readValue("uid");
	static String region_id=PropertiesHandle.readValue("region_id");
	
	public static Map<Integer, List<String>> getMediaID(){
		String url=PropertiesHandle.readValue("newsbaseUrl") + "media/list" + UrlAdd.UrlAdd();
		Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("uid", uid);
        params.put("region_id", region_id);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
                
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject results=final_res.data;
        JSONArray paIds=results.getJSONArray("region_like");//公众号列表
        JSONArray tagIds=results.getJSONArray("tag_like");//频道列表
        
        Map<Integer, List<String>> mediaIDs=new HashMap<Integer, List<String>>();
        List<String> pidList=new ArrayList<>();
        List<String>tidList=new ArrayList<>();
        
        pidList.add(paIds.getJSONObject(1).getString("pa_id"));
        pidList.add(paIds.getJSONObject(2).getString("pa_id"));
        tidList.add(tagIds.getJSONObject(1).getString("tag_id"));
        tidList.add(tagIds.getJSONObject(2).getString("tag_id"));
        
        mediaIDs.put(1, pidList);
        mediaIDs.put(2, tidList);
        
        System.out.println(paIds.getString(1)+paIds.getString(2)+mediaIDs.get(1)+"\n"+tagIds.getString(1)+tagIds.getString(2)+mediaIDs.get(2));
        
        return mediaIDs;
        
        
	}
	
	public static void main(String[] args) {
		GetMediaID.getMediaID();
	}

}
