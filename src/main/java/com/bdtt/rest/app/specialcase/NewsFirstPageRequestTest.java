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
 * 本脚本用于验证首页每次下拉刷新后，本地资讯和全国资讯是否及时更新，没有重复信息。其中本地资讯只适合非人工运营站点（完全有引擎给出省市县卡）
 */

public class NewsFirstPageRequestTest {
	
/*	
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
	String bdread = "bendichengqun-henxin310000";
	String url = "http://app-test.benditoutiao.com/d-news/index/index?bdread=bendichengqun-henxin310000";
*/	
	//pre
	static String mode = "1";
	static String lasttime = "1";
	String uid = "1089473";
	String security = "27afa892af4452a4ff602d04f63a68c3";
	String region_id = "121010";//上海市区
	String android_ios = "2";
	String time = "1505963223474";
	String udid = "3d2805f7966846c59e3104ff16bfa9df";
	String uuid = "ce6ff6df588c4e5b90510c077d4186ed";
	String version = "5.1.0";
	String bdread = "bendichengqun-henxin310000";
	String url = "http://app-build.benditoutiao.com/d-news/index/index?bdread=bendichengqun-henxin310000";
	
	public String getFirstPageNews() {
		
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
        params.put("bdread", bdread);
        
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        mode = "2";
        
        return result;
		
	}
	
	//获取本地资讯，该脚本只适合非人工运营的站点
	public List<String> getLocalNewsList(String result) {
        List<String> localnewslist = new ArrayList<String>();
		
		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        
        JSONArray card_list = dataResult.getJSONArray("card_list");
        //System.out.println(card_list.size());
        
        for (int i=0; i<card_list.size(); i++) {
        	JSONObject card = card_list.getJSONObject(i);
        	//System.out.println("channel id is: " + card.getString("channel_id"));
        	if (card.getString("channel_id").equals("1")) {
        		JSONArray items = card.getJSONArray("items");
        		//System.out.println(items.size());
        		for (int j=0; j<items.size(); j++) {
                    JSONObject item = items.getJSONObject(j);
                    String news_id = item.getString("news_id");
                    localnewslist.add(news_id);
        		}
        	}
        }
        System.out.println(localnewslist);       
        return localnewslist;
        
	}
	
	//获取全国资讯
	public List<String> getNationalNewsList(String result) {
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
	
	public static void main (String[] args) {

		//List<String> totallocalnewslist = new ArrayList<String> ();
		List<String> totalnationalnewslist = new ArrayList<String> ();

		int requesttimes = 5; //请求5次
		NewsFirstPageRequestTest newsfirstpagetest = new NewsFirstPageRequestTest();

		for (int i=0; i<requesttimes; i++) {
			String dataresult = newsfirstpagetest.getFirstPageNews();
			//List<String> totallocalnewslist1 = newsfirstpagetest.getLocalNewsList(dataresult);
			List<String> totalnationalnewslist1 = newsfirstpagetest.getNationalNewsList(dataresult);
			//System.out.println("本地资讯数量为: " + totallocalnewslist1.size());
			System.out.println("全国资讯数量为: " + totalnationalnewslist1.size());
			//totallocalnewslist.addAll(totallocalnewslist1);
			totalnationalnewslist.addAll(totalnationalnewslist1);
		}
		
		//System.out.println(totallocalnewslist);
		System.out.println(totalnationalnewslist);

		
		//查看是否有重复
/*
		String temp = "";
		for (int i=0; i<totallocalnewslist.size(); i++) {
			temp = totallocalnewslist.get(i);
			for (int j=i+1; j<totallocalnewslist.size(); j++) {
				if (temp.equals(totallocalnewslist.get(j))) {
                    System.out.println("本地资讯第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp);
				}
			}
		}
*/		
		String temp1 = "";
		for (int i=0; i<totalnationalnewslist.size(); i++) {
			temp1 = totalnationalnewslist.get(i);
			for (int j=i+1; j<totalnationalnewslist.size(); j++) {
				if (temp1.equals(totalnationalnewslist.get(j))) {
                    System.out.println("全国资讯第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp1);
				}
			}
		}
		
	}
	
}

