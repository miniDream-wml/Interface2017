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
 * 本脚本用于验证首页本地资讯和本地资讯二级页是否有重复
 */

public class LocalNewsFirstPageAndSecondPageRequestTest {
/*	
	//test
	static String mode = "1";
	static String lasttime = "1";
	static String remvduplicate = "";
	String uid = "1234428";
	String security = "5aef2b069d39302f1cfa05f619fcb353";
	String region_id = "121010";//上海市区
	String android_ios = "2";
	String time = "1505282517794";
	String udid = "3d2805f7966846c59e3104ff16bfa9df";
	String uuid = "276ccdbd22144f9daedf11184eeacf93";
	String version = "5.1.0";
	String bdread = "bendichengqun-henxin310000";
*/	
	//pre
	static String mode = "1";
	static String lasttime = "1";
	static String remvduplicate = "";
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
	
	//调首页全国资讯接口
	public String getFirstPageLocalNews() {
		//String url = "http://app-test.benditoutiao.com/d-news/index/index?bdread=bendichengqun-henxin310000";
		String url = "http://app-build.benditoutiao.com/d-news/index/index?bdread=bendichengqun-henxin310000";

        Map<String, String> params = new HashMap<String, String>(); 
        params.put("bdread", bdread);
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
	
	//调二级页本地资讯接口
	public String getSecondPageLocalNews() {
		//String url = "http://app-test.benditoutiao.com/d-news/index/more?bdread=bendichengqun-henxin310000";
		String url = "http://app-build.benditoutiao.com/d-news/index/more?bdread=bendichengqun-henxin310000";
		String channel_id = "1";

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
        params.put("bdread", bdread);
        
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        mode = "2";
        
        return result;
		
	}
	
	//获取首页本地资讯列表
	public List<String> getFirstPageLocalNewsList(String result) {
        List<String> nationalnewslist = new ArrayList<String>();
		
		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        
        JSONArray card_list = dataResult.getJSONArray("card_list");
        
        for (int i=0; i<card_list.size(); i++) {
        	JSONObject card = card_list.getJSONObject(i);
        	if (card.getString("channel_id").equals("1")) {
        		remvduplicate = card.getString("remvduplicate");
        		JSONArray items = card.getJSONArray("items");
        		for (int j=0; j<items.size(); j++) {
                    JSONObject item = items.getJSONObject(j);
                    String news_id = item.getString("news_id");
                    nationalnewslist.add(news_id);
        		}
        	}
        }
        System.out.println("remvduplicate是："+ remvduplicate);
        System.out.println(nationalnewslist);         
        return nationalnewslist;
        
	}
	
	//获取二级页全国资讯列表
	public List<String> getSecondPageLocalNewsList(String result) {
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
		LocalNewsFirstPageAndSecondPageRequestTest localnewsfirstandsecondpagetest = new LocalNewsFirstPageAndSecondPageRequestTest();
		String firstpagedataresult = localnewsfirstandsecondpagetest.getFirstPageLocalNews();
		List<String> firstpagenewslist = localnewsfirstandsecondpagetest.getFirstPageLocalNewsList(firstpagedataresult);
		totallocalnewslist.addAll(firstpagenewslist);

		int requesttimes = 10; //请求3次
		for (int i=0; i<requesttimes; i++) {
			String secondpagedataresult = localnewsfirstandsecondpagetest.getSecondPageLocalNews();
			List<String> secondpagenewslist = localnewsfirstandsecondpagetest.getSecondPageLocalNewsList(secondpagedataresult);
			totallocalnewslist.addAll(secondpagenewslist);
		}
		
		System.out.println(totallocalnewslist);
		
		//查看是否有重复	
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

