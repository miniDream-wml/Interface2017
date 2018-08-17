package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetFlowListNewsId;
import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.MySqlTest;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.RegisterUser;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;

import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by tangqiliang on 2017/12/25.
 * 奖励金币/零钱（5.4版本赚钱阅读新加入）
 */

public class RewardGoldTest {
	
	String testUrl="reward/gold";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String uid=PropertiesHandle.readValue("uid");
	String type; //文章阅读-1 文章分享-2 新手视频-3 晒收入-4 新注册用户-5（客户端不用）
	String news_id;
	
	@DataProvider(name="extrue")
	public Object[][] ParameterTrue() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	//{uid, "1", GetFlowListNewsId.getNewsID(1), "1", "success"},
        	//{uid, "2", GetFlowListNewsId.getNewsID(1), "1", "success"},
        	//{uid, "3", GetFlowListNewsId.getNewsID(1), "1", "success"},
        	//{uid, "4", GetFlowListNewsId.getNewsID(1), "1", "success"},
        	//254320391,334893151
        	{GetMySql.getUserId("13011111111"), "1", "254320391", "1", "success"},//阅读文章1
        	{GetMySql.getUserId("13011111111"), "1", "334893151", "1", "success"},//阅读文章2
        	{GetMySql.getUserId("13011111111"), "2", "254320391", "1", "success"},//分享文章
        	{GetMySql.getUserId("13011111111"), "3", "254320391", "1", "success"},//新手视频
        	{GetMySql.getUserId("13011111111"), "4", "254320391", "1", "success"},//晒收入
        }; 
	}
	
	@DataProvider(name="exfalse")
	public Object[][] ParameterFalse() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	//{uid, "1", GetFlowListNewsId.getNewsID(1), "1", "success"},
        	//{uid, "2", GetFlowListNewsId.getNewsID(1), "1", "success"},
        	//{uid, "3", GetFlowListNewsId.getNewsID(1), "1", "success"},
        	//{uid, "4", GetFlowListNewsId.getNewsID(1), "1", "success"},
        	//254320391,334893151
        	{GetMySql.getUserId("13011111111"), "1", "254320391", "10000", "该用户当天已获得过阅读该新闻的奖励"},
        	{GetMySql.getUserId("13011111111"), "2", "254320391", "0", "4小时内重复分享只奖励一次金币哦"},
        	{GetMySql.getUserId("13011111111"), "3", "254320391", "10000", "该任务已完成"},//新手视频奖励已完成过
        	{uid, "3", "254320391", "10000", "该任务已完成"},//非新手，没有新手视频奖励
        	{GetMySql.getUserId("13011111111"), "4", "254320391", "10000", "该用户当天已获得过晒收入的奖励"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		System.out.println("签到接口测试开始！");
		MySqlTest.delete_user(GetMySql.getUserId("13011111111"));//删除用户
		RegisterUser.registerUser("13011111111", "123456", "test");//新注册用户，参数为用户名，密码，以及环境，环境是test, pre或者online
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("签到接口测试结束！");
	}
	
	@Test(dataProvider="extrue", priority=1)
	public void rewardGoldTest(String uid, String type, String news_id, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("type", type);
        params.put("news_id", news_id);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
        
        ArrayList<Integer> rewardgoldlist = new ArrayList<Integer>(Arrays.asList(10, 300, 200, 30));//文章阅读奖励10，文章分享奖励300，新手视频奖励200，晒收入奖励30
        Assert.assertEquals(final_res.data.getJSONObject("reward").getInt("num"), (int)rewardgoldlist.get(Integer.parseInt(type)-1));//验证金币金额正确
		
	}
	
	@Test(dataProvider="exfalse", priority=2)
	public void rewardGoldFalseTest(String uid, String type, String news_id, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("type", type);
        params.put("news_id", news_id);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}	
}


