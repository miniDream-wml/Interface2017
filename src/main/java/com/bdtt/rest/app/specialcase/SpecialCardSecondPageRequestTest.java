package com.bdtt.rest.app.specialcase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bdtt.rest.util.GetMySql;
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

public class SpecialCardSecondPageRequestTest {

	String mode="1";//下拉刷新
	String lasttime="";
	String index="";
	String channel_id="2";//全国资讯
	static String offset="";
	
	public String getAllnews() {
		String url = PropertiesHandle.readValue("newsbaseUrl") + "index/more" + UrlAdd.UrlAdd();
		System.out.println("mode=="+mode+"lasttime=="+lasttime+"index=="+index+"channelID=="+channel_id);
		Map<String, String> params=ParamsBuilt.params_built();
		params.put("mode", mode);
		params.put("lasttime", lasttime);
		params.put("index", index);
		params.put("channel_id", channel_id);
		params.put("offset", offset);
		
		String result = HTTPPost.httpPostForm(url, params);
        System.out.println("接口返回结果："+result);
        
        return result;
	
	}
	
	public List getSpecialcardList(String result,int requesttimes) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        List<List> specialcardlist = new ArrayList<List> ();
		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult=final_res.data;
        JSONArray items = dataResult.getJSONArray("items");
        
        lasttime= dataResult.getString("last_time");       
        index = dataResult.getString("index");
                
        for (int i=0; i<items.size(); i++) {
            JSONObject item = items.getJSONObject(i);
            if (item.getInt("style")==12) {
                String position = String.valueOf(requesttimes*10+i+1);//记录精品卡的位置
            	String specially_id = String.valueOf(item.getInt("specially_id"));
                String news_id = item.getString("news_id");
                String ratio = GetMySql.getSpecialCardRatio(news_id);
        		List <String> specialcard = new ArrayList<String>();
                specialcard.add(position);
                specialcard.add(specially_id);
                specialcard.add(news_id);
                specialcard.add(ratio);
                specialcardlist.add(specialcard);
			}
        }
                
        return specialcardlist;
        
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		
		Map <Integer, List> specialcardmap = new HashMap<Integer, List> ();
		int requesttimes = 10; //请求服务器次数

		SpecialCardSecondPageRequestTest sCardTest=new SpecialCardSecondPageRequestTest();
		for (int i=0; i<requesttimes; i++) {
			offset = String.valueOf(i*10);
			String result = sCardTest.getAllnews();
			List<List> specialcardlist = sCardTest.getSpecialcardList(result, i);
			System.out.println("本次请求精品卡列表: " + specialcardlist);
			specialcardmap.put(i+1, specialcardlist);
		}
		
		//System.out.println(specialcardmap);
		for (Entry<Integer, List> s : specialcardmap.entrySet()) {
			System.out.println(s);
		}

	}
}

