package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.MySqlTest;
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
 * Created by tangqiliang on 2017/10/24.
 * 接任务：接受/领取赚钱阅读活动，5.5加入
 */

public class ActivityReceiveActivityTest {
	
	String testUrl="activity/receiveActivity";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	
	String uid=PropertiesHandle.readValue("uid");
	String act_id;//活动的id, 只能从数据库里获取，可能会变
	
	@DataProvider(name="extrue")
	public Object[][] ParameterTrue() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{uid, GetMySql.getTaskActId("10800元"), "1", "success"},
        	{uid, GetMySql.getTaskActId("新年小目标"), "1", "success"},
        	{GetMySql.getUserId("13000000000"), GetMySql.getTaskActId("10800元"), "1", "success"},
        	{GetMySql.getUserId("13000000000"), GetMySql.getTaskActId("新年小目标"), "1", "success"},
        }; 
	}
	
	@DataProvider(name="exfalse")
	public Object[][] ParameterFalse() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
    		{uid, GetMySql.getTaskActId("10800元"), "0", "该任务已经领取过,不能重复领取!"},
    		{uid, "111111", "0", "该活动不存在!"},
        	{uid, "", "0", "活动ID不能为空!"},
        	{"", GetMySql.getTaskActId("10800元"), "0", "请先登录!"},
        	{"", GetMySql.getTaskActId("新年小目标"), "0", "请先登录!"},
        	//{"1111111111", GetMySql.getTaskActId("新年小目标"), "0", "该任务已经领取过,不能重复领取!"},//目前并没有判断uid是否正确
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		System.out.println("接任务接口测试开始！");
		MySqlTest.delete_user_activity_receiver(PropertiesHandle.readValue("uid"));//删除领取活动的记录
		MySqlTest.delete_user_activity_receiver(GetMySql.getUserId("13000000000"));//删除领取活动的记录
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("接任务接口测试结束！");
	}
	
	@Test(dataProvider="extrue", priority=1, enabled=true)
	public void activityReceiveActivityTest(String uid, String act_id, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("act_id", act_id);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code, code);
        Assert.assertEquals(final_res.msg, exmsg);
        Assert.assertEquals(final_res.data.getInt("isReceive"), 1);
        Assert.assertTrue(GetMySql.getReceivedTaskIdList(uid).contains(act_id));//从local_money_activity_receivers表中，验证用户已接受活动
	}
	
	@Test(dataProvider="exfalse", priority=2, enabled=true)
	public void activityReceiveActivityFalseTest(String uid, String act_id, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("act_id", act_id);

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


