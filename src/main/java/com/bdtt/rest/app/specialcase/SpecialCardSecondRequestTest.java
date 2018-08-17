package com.bdtt.rest.app.specialcase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author mouhaiyan
 *此脚本用于验证全国资讯二级界面精品卡位置的展示和判断精品卡文章展示是否重复
 *
 */

public class SpecialCardSecondRequestTest {

	String mode="2";
	String lasttime="";
	String index="";
	String channel_id="2";
	
	public String getAllnews() {
		String url=PropertiesHandle.readValue("newsbaseUrl")+"index/more"+UrlAdd.UrlAdd();
		System.out.println("mode=="+mode+"lasttime=="+lasttime+"index=="+index+"channelID=="+channel_id);
		Map<String, String> params=ParamsBuilt.params_built();
		params.put("mode", mode);
		params.put("lasttime", lasttime);
		params.put("index", index);
		params.put("channel_id", channel_id);
		
		String result = HTTPPost.httpPostForm(url, params);
//        System.out.println("接口返回结果："+result);
        
        return result;
	
	}
	
	public List<String> getSpecialcardList(String result,int requesttimes) {
        List<String> sCardlist = new ArrayList<String>();
		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult=final_res.data;
        JSONArray news_list = dataResult.getJSONArray("items");
        
        lasttime= dataResult.getString("last_time");       
        index = dataResult.getString("index");
                
        for (int i=0; i<news_list.size(); i++) {
            JSONObject feed = news_list.getJSONObject(i);
            int news_style = feed.getInt("style");
            String card_name=feed.optString("specially_name"); 
            int position=requesttimes*10+i+1;
//         	System.out.println("资讯类型"+news_style);
            if (news_style==12) {
            	sCardlist.add(feed.get("news_id").toString());
            	System.out.println("精品卡名称："+card_name+"精品卡位置："+position);
			}
        }
                
        return sCardlist;
        
	}
	
	public static void main(String[] args) {
		List<String> sCardlist = new ArrayList<String> ();
		int requesttimes = 3; //请求3次
		SpecialCardSecondRequestTest sCardTest=new SpecialCardSecondRequestTest();
		for(int i=0;i<requesttimes;i++){
			String result=sCardTest.getAllnews();
			List<String> sCardlistChird=sCardTest.getSpecialcardList(result,i);
			sCardlist.addAll(sCardlistChird);
		}
		
		//查看是否有重复
				String temp = "";
				for (int i=0; i<sCardlist.size(); i++) {
					temp = sCardlist.get(i);
					for (int j=i+1; j<sCardlist.size(); j++) {
						if (temp.equals(sCardlist.get(j))) {
		                    System.out.println("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp);
						}
					}
				}
	}
	
	/**
	 * 
	 * 用于精品卡列表获取ID的
	 */
	public String getSpecialID() {
		List<String> sCardlist = new ArrayList<String> ();
		int requesttimes = 2; 
		SpecialCardSecondRequestTest sCardTest=new SpecialCardSecondRequestTest();
		for(int i=0;i<requesttimes;i++){
			String result=sCardTest.getAllnews();
			List<String> sCardlistChird=sCardTest.getSpecialcardList(result,i);
			sCardlist.addAll(sCardlistChird);
		}
		String specialId=sCardlist.get(1);
		return specialId;
	}
	
}

