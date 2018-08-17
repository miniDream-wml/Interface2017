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
 * 本脚本用于验证首页全国资讯和全国资讯二级页资讯是否有重复
 */

public class NewsFirstPageAndSecondPageRequestTest {
	
	//test
	static String mode = "1";
	static String lasttime = "1";
	String uid = "1234428";
	String security = "5aef2b069d39302f1cfa05f619fcb353";
	String region_id = "121010";//上海市区
	String android_ios = "2";
	String time = "1505282517794";
	String udid = "3d2805f7966846c59e3104ff16bfa9df";
	String uuid = "276ccdbd22144f9daedf11184eeacf93";
	String version = "5.1.0";
	
	//调首页全国资讯接口
	public String getFirstPageNews() {
		String url = "http://app-test.benditoutiao.com/d-news/index/index?bdread=bendichengqun-henxin310000";

        Map<String, String> params = new HashMap<String, String>(); 
        params.put("mode", mode);
        params.put("lasttime", lasttime);
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
        
        return result;
		
	}
	
	//调二级页全国资讯接口
	public String getSecondPageNews() {
		String url = "http://app-test.benditoutiao.com/d-news/index/more?bdread=bendichengqun-henxin310000";
		String channel_id = "2";

        Map<String, String> params = new HashMap<String, String>(); 
        params.put("mode", mode);
        params.put("lasttime", lasttime);
        params.put("channel_id", channel_id);
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
	
	//获取首页全国资讯列表
	public List<String> getFirstPageNewsList(String result) {
        List<String> nationalnewslist = new ArrayList<String>();
		
		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        
        JSONArray card_list = dataResult.getJSONArray("card_list");
        
        for (int i=0; i<card_list.size(); i++) {
        	JSONObject card = card_list.getJSONObject(i);
        	if (card.getString("channel_id").equals("2")) {
        		JSONArray items = card.getJSONArray("items");
        		for (int j=0; j<items.size(); j++) {
                    JSONObject item = items.getJSONObject(j);
                    String news_id = item.getString("news_id");
                    nationalnewslist.add(news_id);
        		}
        	}
        }
        System.out.println(nationalnewslist);         
        return nationalnewslist;
        
	}
	
	//获取二级页全国资讯列表
	public List<String> getSecondPageNewsList(String result) {
        List<String> nationalnewslist = new ArrayList<String>();
		
		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        
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

		List<String> totalnationalnewslist = new ArrayList<String> ();

		int requesttimes = 2; //请求2次
		NewsFirstPageAndSecondPageRequestTest newsfirstandsecondpagetest = new NewsFirstPageAndSecondPageRequestTest();

		for (int i=0; i<requesttimes; i++) {
			String firstpagedataresult = newsfirstandsecondpagetest.getFirstPageNews();
			List<String> firstpagenewslist = newsfirstandsecondpagetest.getFirstPageNewsList(firstpagedataresult);
			String secondpagedataresult = newsfirstandsecondpagetest.getSecondPageNews();
			List<String> secondpagenewslist = newsfirstandsecondpagetest.getSecondPageNewsList(secondpagedataresult);
			totalnationalnewslist.addAll(firstpagenewslist);
			totalnationalnewslist.addAll(secondpagenewslist);
		}
		
		System.out.println(totalnationalnewslist);
		
		//查看是否有重复	
		String temp = "";
		for (int i=0; i<totalnationalnewslist.size(); i++) {
			temp = totalnationalnewslist.get(i);
			for (int j=i+1; j<totalnationalnewslist.size(); j++) {
				if (temp.equals(totalnationalnewslist.get(j))) {
                    System.out.println("全国资讯第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp);
				}
			}
		}
		
	}
	
}

