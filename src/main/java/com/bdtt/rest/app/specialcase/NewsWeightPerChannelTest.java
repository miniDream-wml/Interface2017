package com.bdtt.rest.app.specialcase;

import java.text.NumberFormat;
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
 * 本脚本用于测试用户订阅的频道的权重，方法是通过请求300条全国资讯，获取每条资讯的tag值，计算每个tag出现的次数及权重 
 */

public class NewsWeightPerChannelTest {
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
	String uuid = "20fccbec5a65465991027452122e2e6a";
	String version = "5.1.0";	
	String url = "http://app-test.benditoutiao.com/d-news/index/more?bdread=bendichengqun-henxin310000";
*/
	//pre
	static String mode = "1";
	String lasttime = "1";
	String channel_id = "2";
	//String uid = "1089473";
	String security = "27afa892af4452a4ff602d04f63a68c3";
	String region_id = "121010";//上海市区
	String android_ios = "2";
	String time = "1505963223474";
	String udid = "3d2805f7966846c59e3104ff16bfa9df";
	String uuid = "a9f2acfc191143fa82f833463a326ab5";
	String version = "5.1.0";
	String url = "http://app-build.benditoutiao.com/d-news/index/more?bdread=bendichengqun-henxin310000";
		
	
	//调用全国资讯二级页面接口
	public String getNews() {
        Map<String, String> params = new HashMap<String, String>(); 
        params.put("mode", mode);
        params.put("lasttime", lasttime);
        params.put("channel_id", channel_id);
        //params.put("uid", uid);
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
	public List<String> getTagList(String result) {
        List<String> taglist = new ArrayList<String>();
		
		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;
        
        JSONArray items = dataResult.getJSONArray("items");
        for (int i=0; i<items.size(); i++) {
        	JSONObject item = items.getJSONObject(i);
            String tag = item.getString("tag");
            taglist.add(tag);
        }
        System.out.println(taglist);         
        return taglist;
        
	}
	
	//计算tag list中每个tag出现的次数，并生成一个map
	public Map<String, Integer> getTagCountMap (List<String> taglist) {
		Map<String, Integer> tagcountmap = new HashMap<String, Integer>();
		
		for (int i=0; i<taglist.size(); i++) {
			if (tagcountmap.containsKey(taglist.get(i))) {
				tagcountmap.put(taglist.get(i), tagcountmap.get(taglist.get(i)).intValue() + 1);
			} else {
				tagcountmap.put(taglist.get(i), 1);
			}		
		}
		
		return tagcountmap;
	}
	
	//计算每个tag的权重
	public void calculateTagWeight (Map<String, Integer> tagcountmap) {
		int sum = 0;
		for (String key : tagcountmap.keySet()) {
			//System.out.println(key + ": " + tagcountmap.get(key));
			sum += tagcountmap.get(key).intValue();	
		}
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		
		for (String key : tagcountmap.keySet()) {
			System.out.println(key + ": " + tagcountmap.get(key) + "次, 权重为: " + numberFormat.format((float) tagcountmap.get(key).intValue() / (float) sum * 100) + "%");	
		}
		
	}
	
	public static void main (String[] args) {
		List<String> totaltaglist = new ArrayList<String> ();
		NewsWeightPerChannelTest newsweightperchannel = new NewsWeightPerChannelTest();
		
		int requesttimes = 30; //请求30次
		for (int i=0; i<requesttimes; i++) {
			String dataresult = newsweightperchannel.getNews();
			List<String> taglist = newsweightperchannel.getTagList(dataresult);
			System.out.println("tag数量为: " + taglist.size());
			totaltaglist.addAll(taglist);
		}
		
		System.out.println("tag的总数量为: " + totaltaglist.size());
		
		Map <String, Integer> tagcountmap = newsweightperchannel.getTagCountMap(totaltaglist);
		newsweightperchannel.calculateTagWeight(tagcountmap);

/*
		totaltaglist.add("我");
		totaltaglist.add("你");
		totaltaglist.add("我");
		totaltaglist.add("我");
		totaltaglist.add("他");
		totaltaglist.add("他");
		totaltaglist.add("你");
		totaltaglist.add("我");
		totaltaglist.add("我");
		totaltaglist.add("我");
		
		Map <String, Integer> tagcountmap = newsweightperchannel.getTagCountMap(totaltaglist);
		newsweightperchannel.calculateTagWeight(tagcountmap);
*/
	}
	
}

