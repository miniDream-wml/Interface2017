package com.bdtt.rest.app.news;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;

import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by tangqiliang on 2017/12/18.
 * 新版资讯信息流接口（分栏目）(5.3.5版本及之后）
 */

public class FlowListTest {
	
	String testUrl="flow/list";
	String baseUrl=PropertiesHandle.readValue("newsbaseUrl");
	String channel_id; //1-推荐， 2-本地， 其他订阅的tag
	String mode; //1 - 自动刷新， 2 - 下拉 3 - 上拉
	String last_time; //刷新时间小于20分钟时返回空
	String offset; //当前已展示资讯数
	String ad_offset; //当前频道已刷新广告数
	String score; //在本地频道，需要传，其他传空就好
		
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{"1", "1", "", "0", "0", "", "1", "success"},
        	{"2", "1", "", "0", "0", "", "1", "success"},
        	//{"", GetCircleFeedId.getCircleFeedId(), "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("新版资讯信息流接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("新版资讯信息流接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String channel_id, String mode, String last_time, String offset, String ad_offset, String score, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        //params.put("uid", uid);
        params.put("channel_id", channel_id);
        params.put("mode", mode);
        params.put("last_time", last_time);
        params.put("offset", offset);
        params.put("ad_offset", ad_offset);
        params.put("score", score);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
 /*       
      //判断app首页本地圈推广位／本地资讯／全国资讯是否存在
        JSONObject res=final_res.getData();
		JSONObject circleObj=res.getJSONObject("circle");
		if (circleObj.optString("feed_id").isEmpty()) {
			System.out.println("本地圈推广位返回为空啦！！");
		}
		
		JSONArray arts=res.getJSONArray("card_list"); 
		JSONArray  localart=arts.getJSONObject(0).getJSONArray("items");//本地资讯
		JSONArray  globalart=arts.getJSONObject(1).getJSONArray("items");//全国资讯
		
		if (localart.isEmpty()) {
			System.out.println("本地资讯返回为空啦！！");
		}else {
			int locallen=localart.size();
			System.out.println("本地资讯条数："+locallen);
		}
		if (globalart.isEmpty()) {
			System.out.println("全国资讯返回为空啦！！");
		}else {
			int glioballen=globalart.size();
			System.out.println("全国资讯条数："+glioballen);
			
			//输出精品卡的位置
			for(int i=0;i<globalart.size();i++) {
				JSONObject specialObj =(JSONObject) globalart.get(i);
				int position=i+1;
				int style=specialObj.getInt("style");
				if (style==12) {
					System.out.println("精品卡位置："+position+"\t精品卡名称:"+specialObj.getString("specially_name")+"\t文章名称:"+specialObj.getString("title")+"\t文章ID:"+specialObj.getString("news_id"));
				}
			}
		}
*/		
		
	}	
}





