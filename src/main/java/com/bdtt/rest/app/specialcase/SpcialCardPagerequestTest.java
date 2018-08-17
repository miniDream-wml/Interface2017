package com.bdtt.rest.app.specialcase;

import java.util.HashMap;
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
 * 此脚本用于验证全国资讯首页展示的精品卡位置
 *
 */
public class SpcialCardPagerequestTest {

	String  mode="1";//刷新
	String lasttime="";
	String index="";
	String remvduplicate="";//用于资讯去重
	
	/**
	 * 获取服务器返回的结果
	 * @return
	 */
	public String getAllnews() {
		String url=PropertiesHandle.readValue("newsbaseUrl")+"index/index"+UrlAdd.UrlAdd();
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
	 * 获取精品卡名称和位置的map
	 * 获取位置和精品卡文章ID的map
	 * @param result
	 * @param requesttimes
	 * @return
	 */
	public Map<Integer,Map<Integer, String>> getSpecialcardList(String result) {
//        List<String> sCardlist = new ArrayList<String>();
		Map<Integer,Map<Integer, String>> addMap=new HashMap<>();
		Map<Integer, String>sCardlist=new HashMap<>();
		Map<Integer, String>sNewsList=new HashMap<>();
		Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONArray dataResult = final_res.data.optJSONArray("card_list");
        lasttime= final_res.data.optString("last_time");
        
        JSONObject resultObj=new JSONObject();
        
        for(int i=0;i<dataResult.size();i++) {
        	JSONObject feed=dataResult.getJSONObject(i);
        	if (feed.optString("channel_id").equals("2")) {
				resultObj=dataResult.getJSONObject(i);
			}
        	
        }
       
        System.out.println("全国资讯内容："+resultObj.toString());
        index = resultObj.optString("index");
        remvduplicate=resultObj.optString("remvduplicate");
        
        JSONArray news_list=resultObj.getJSONArray("items");
        for (int i=0; i<news_list.size(); i++) {
            JSONObject feed = news_list.getJSONObject(i);
            int  news_style = feed.getInt("style");
            String card_name=feed.getString("specially_name").toString(); 
            String title=feed.getString("title");
            int position=i+1;
//         	System.out.println("资讯类型"+news_style);
            if (news_style==12) {
            	sCardlist.put(position, card_name);
            	sNewsList.put(position, feed.get("news_id")+"");
            	addMap.put(1, sCardlist);
            	addMap.put(2, sNewsList);
            	System.out.println("精品卡名称："+card_name+"\n"+"精品卡位置："+position+"\n"+"资讯文章的名称："+title);
			}
        }
                
        return addMap;
        
	}
	
	
	public static void main(String[] args) {
		Map<Integer,Map<Integer, String>> addMap=new HashMap<>();
		Map<Integer, String> sCardlist = new HashMap<>();
		Map<Integer, String> sNewsList = new HashMap<>();
		int requesttimes = 2; //请求服务器次数
		SpcialCardPagerequestTest sCardTest=new SpcialCardPagerequestTest();
		for(int i=0;i<requesttimes;i++){
			String result=sCardTest.getAllnews();
			Map<Integer,Map<Integer, String>> sMapChird=sCardTest.getSpecialcardList(result);
			System.out.println("第"+i+"次请求服务器");
			if (i==0) {
				addMap.clear();
				addMap.putAll(sMapChird);
				sCardlist=addMap.get(1);
				sNewsList=addMap.get(2);
			}else {
				Map<Integer, String> cMapChird=sMapChird.get(1);
				Map<Integer, String> nMapChird=sMapChird.get(2);
				if (cMapChird.equals(sCardlist)) {
						//如果显示的精品卡内容和位置一样时，判断展示的资讯是否一致
					if (nMapChird.equals(sNewsList)) {
						System.out.println("精品卡显示的文章重复");
					}
				}else {
						System.out.println("精品卡位置显示错误！！！！！！！！！！");
				}
			}
			
		}
		
	}
}
