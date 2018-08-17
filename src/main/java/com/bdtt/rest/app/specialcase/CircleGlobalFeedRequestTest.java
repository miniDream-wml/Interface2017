package com.bdtt.rest.app.specialcase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.TestJSonResult;
import com.google.gson.Gson;

/**
 * Created by tangqiliang on 2017/9/13.
 * 本脚本用于验证兴化-全部动态模式下，本地圈动态流每次上滑加载后不出现重复动态
 */

public class CircleGlobalFeedRequestTest {
	
	//online
	static String mode = "1";
	String uid = "1089473";
	String security = "16a09b76039a5055546f817fcab43e99";
	String region_id = "112111";
	static String index = "1";
	String android_ios = "2";
	String time = "1505200713726";
	String udid = "3d2805f7966846c59e3104ff16bfa9df";
	String uuid = "276ccdbd22144f9daedf11184eeacf93";
	String version = "5.1.0";
	
	
	public String getGlobalSquareFeeds() {
		
    	//String url = "http://app.benditoutiao.com/bdq/Circle/getGlobalSquareFeeds?bdread=bendichengqun-henxin310000";
    	String url = "http://app.benditoutiao.com/bdq/Circle/getGlobalSquareFeeds";

		String circle_mode = "2";
      
        Map<String, String> params = new HashMap<String, String>(); 
        params.put("mode", mode);
        params.put("uid", uid);
        params.put("security", security);
        params.put("region_id", region_id);
        params.put("index", index);
        params.put("android_ios", android_ios);
        params.put("time", time);
        params.put("udid", udid);
        params.put("uuid", uuid);
        params.put("version", version);
        params.put("circle_mode", circle_mode);
        
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        return result;
		
	}
	
	public List<String> getFeedList(String result) {
        List<String> feedlist = new ArrayList<String>();
		
		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        
        JSONArray feed_list = (JSONArray) dataResult.get("feed_list");
        index = dataResult.getString("index");
        mode = "2";
        for (int i=0; i<feed_list.size(); i++) {
            JSONObject feed = feed_list.getJSONObject(i);
            String feed_id = feed.get("feed_id").toString();
            //System.out.println(feed_id);
            feedlist.add(feed_id);
        }
                
        return feedlist;
        
	}
	
	public static void main (String[] args) {

		List<String> feedlist = new ArrayList<String> ();
		int requesttimes = 5; //请求5次
		CircleGlobalFeedRequestTest globalfeedtest = new CircleGlobalFeedRequestTest();

		for (int i=0; i<requesttimes; i++) {
			String dataresult = globalfeedtest.getGlobalSquareFeeds();
			List<String> feedlist1 = globalfeedtest.getFeedList(dataresult);
			System.out.println(feedlist1.size());
			//System.out.println(index);
			//System.out.println(mode);
			feedlist.addAll(feedlist1);
		}
		
/*		
		List<String> feedlist = new ArrayList<String> ();
		feedlist.add("2850f021dc258dc2668056866997846d");
		feedlist.add("9f59aee19e4f8cc80df344a454c2e3e8");
		feedlist.add("1a63e664917f58dc5dbc57eae69d9d9e");
		feedlist.add("fd07b1c2a2abb307774093eee6adb3e6");
		feedlist.add("9f59aee19e4f8cc80df344a454c2e3e8");
		feedlist.add("1a63e664917f58dc5dbc57eae69d9d9e");
*/		
		System.out.println(feedlist);
		
		//查看是否有重复
		String temp = "";
		for (int i=0; i<feedlist.size(); i++) {
			temp = feedlist.get(i);
			for (int j=i+1; j<feedlist.size(); j++) {
				if (temp.equals(feedlist.get(j))) {
                    System.out.println("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp);
				}
			}
		}
		
	}
	
}

