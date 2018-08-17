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
 * Created by tangqiliang on 2017/9/14.
 * 本脚本用于测试本地资讯二级页面是否有数据重复，方法是通过请求多次本地资讯二级页面，获取每条资讯的news_id值，再判断这些news_id是否有重复
 */

public class LocalNewsSecondPageRequestTest {
/*	
	//test
	static String mode = "1";
	String lasttime = "1";
	String channel_id = "2";
	String uid = "1234428";
	String security = "5aef2b069d39302f1cfa05f619fcb353";
	String region_id = "121010";//上海市区
	String android_ios = "2";
	String time = "1505282517794";
	String udid = "3d2805f7966846c59e3104ff16bfa9df";
	String uuid = "276ccdbd22144f9daedf11184eeacf93";
	String version = "5.1.0";	
	String url = "http://app-test.benditoutiao.com/d-news/index/more?bdread=bendichengqun-henxin310000";
*/	
	//pre
	static String mode = "1";
	String lasttime = "1";
	String channel_id = "1";
	String remvduplicate = "";
	static String index = "";
	String uid = "1089473";
	String security = "27afa892af4452a4ff602d04f63a68c3";
	//String region_id = "121010";//上海市区
	String region_id = "112111";//兴化
	String android_ios = "2";
	String time = "1505963223474";
	String udid = "3d2805f7966846c59e3104ff16bfa9df";
	String uuid = "ce6ff6df588c4e5b90510c077d4186ed";
	String version = "5.1.0";
	String bdread = "bendichengqun-henxin310000";
	String url = "http://app-build.benditoutiao.com/d-news/index/more?bdread=bendichengqun-henxin310000";
	
	
	//调用全国资讯二级页面接口
	public String getNews() {
        Map<String, String> params = new HashMap<String, String>(); 
        params.put("mode", mode);
        params.put("lasttime", lasttime);
        params.put("channel_id", channel_id);
        params.put("remvduplicate", remvduplicate);
        params.put("index", index);
        params.put("uid", uid);
        params.put("security", security);
        params.put("region_id", region_id);
        params.put("android_ios", android_ios);
        params.put("time", time);
        params.put("udid", udid);
        params.put("uuid", uuid);
        params.put("version", version);
        
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        mode = "2";
        
        return result;
		
	}
		
	//获取每次请求的tag，生成一个tag list
	public List<String> getLocalNewsList(String result) {
        List<String> nationalnewslist = new ArrayList<String>();
		
		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        index = dataResult.getString("index");
        
        JSONArray items = dataResult.getJSONArray("items");
        for (int i=0; i<items.size(); i++) {
        	JSONObject item = items.getJSONObject(i);
            String news_id = item.getString("news_id");
            nationalnewslist.add(news_id);
        }
        System.out.println(nationalnewslist);         
        return nationalnewslist;
        
	}
	
	
	public static void main (String[] args) {
		List<String> totallocalnewslist = new ArrayList<String> ();
		LocalNewsSecondPageRequestTest localnewssecondpagetest = new LocalNewsSecondPageRequestTest();
		
		int requesttimes = 10; //请求10次
		for (int i=0; i<requesttimes; i++) {
			String dataresult = localnewssecondpagetest.getNews();
			List<String> localnewslist = localnewssecondpagetest.getLocalNewsList(dataresult);
			System.out.println("news数量为: " + localnewslist.size());
			totallocalnewslist.addAll(localnewslist);
		}
		
		System.out.println("news的总数量为: " + totallocalnewslist.size());
		
		String temp = "";
		for (int i=0; i<totallocalnewslist.size(); i++) {
			temp = totallocalnewslist.get(i);
			for (int j=i+1; j<totallocalnewslist.size(); j++) {
				if (temp.equals(totallocalnewslist.get(j))) {
                    System.out.println("本地资讯第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp);
				}
			}
		}
		
	}
	
}

