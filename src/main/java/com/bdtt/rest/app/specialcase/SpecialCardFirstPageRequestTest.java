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
 * @author Ryan Tang
 * 此脚本用于验证全国资讯首页展示的精品卡名称、位置，文章，以及文章的曝光率
 *
 */
public class SpecialCardFirstPageRequestTest {

	String mode="2";//下拉刷新
	String lasttime="";
	String index="";
	String remvduplicate="";//用于资讯去重
	
	/**
	 * 获取服务器返回的结果
	 * @return
	 */
	public String getAllnews() {
		String url = PropertiesHandle.readValue("newsbaseUrl") + "index/index" + UrlAdd.UrlAdd();
		System.out.println("index:"+index+"==mode:"+mode+"==lasttime:"+lasttime+"==remvduplicate:"+remvduplicate);
		Map<String, String> params=ParamsBuilt.params_built();
		params.put("mode", mode);
		params.put("lasttime", lasttime);
		params.put("index", index);
		params.put("remvduplicate", remvduplicate);
		
		String result = HTTPPost.httpPostForm(url, params);
        System.out.println("接口返回结果："+result);
        
        return result;
	
	}
	
	/**
	 * 获取精品卡名称、位置、文章ID、以及曝光率
	 * 获取位置和精品卡文章ID的map
	 * @param result
	 * @param requesttimes
	 * @return
	 * @throws SQLException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	public List getSpecialCardList(String result) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		List <List> specialcardlist = new ArrayList<List>();

		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONArray card_list = final_res.data.optJSONArray("card_list");
        lasttime= final_res.data.optString("last_time");
        
        for (int i=0; i<card_list.size(); i++) {
        	JSONObject card = card_list.getJSONObject(i);
        	if (card.getString("channel_id").equals("2")) {//2为全国资讯
        		JSONArray items = card.getJSONArray("items");
        		for (int j=0; j<items.size(); j++) {
                    JSONObject item = items.getJSONObject(j);
                    if(item.getInt("style")==12){//12为精品卡
                        String position = String.valueOf(j+1);
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
        	}
        }
		return specialcardlist;
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		
		Map <Integer, List> specialcardmap = new HashMap<Integer, List> ();
		int requesttimes = 10; //请求服务器次数

		SpecialCardFirstPageRequestTest sCardTest=new SpecialCardFirstPageRequestTest();
		for (int i=0; i<requesttimes; i++) {
			String result = sCardTest.getAllnews();
			List<List> specialcardlist = sCardTest.getSpecialCardList(result);
			System.out.println("本次请求精品卡列表: " + specialcardlist);
			specialcardmap.put(i+1, specialcardlist);
		}
		
		//System.out.println(specialcardmap);
		for (Entry<Integer, List> s : specialcardmap.entrySet()) {
			System.out.println(s);
		}		
	}
}
