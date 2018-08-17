package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetMySql;
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
 * Created by tangqiliang on 2017/10/24.
 * 10800活动：赚钱阅读活动，5.5加入
 */

public class ActivityBigTargetTest {
	
	String testUrl="activity/bigTarget";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	
	String uid=PropertiesHandle.readValue("uid");
	String act_id;//活动的id, 只能从数据库里获取，可能会变
	
	@DataProvider(name="extrue")
	public Object[][] ParameterTrue() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{uid, GetMySql.getTaskActId("10800元"), "1", "success"},
        	{GetMySql.getUserId("13000000000"), GetMySql.getTaskActId("新年小目标"), "1", "success"},
        }; 
	}
	
	@DataProvider(name="exfalse")
	public Object[][] ParameterFalse() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{uid, "111111", "0", "该活动不存在!"},
        	{uid, "", "0", "活动ID不能为空!"},
        	{"", GetMySql.getTaskActId("10800元"), "1", "success"},//返回正确
        	{"", GetMySql.getTaskActId("新年小目标"), "1", "success"},//返回正确
        	{"1111111111", GetMySql.getTaskActId("新年小目标"), "1", "success"},//返回正确
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("10800活动接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("10800活动接口测试结束！");
	}
	
	@Test(dataProvider="extrue", priority=1, enabled=true)
	public void activityNewYearTest(String uid, String act_id, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
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
        Assert.assertNotNull(final_res.data.getString("startTime"));
        Assert.assertNotNull(final_res.data.getString("endTime"));
        Assert.assertNotNull(final_res.data.getString("horseLamp"));
        Assert.assertNotNull(final_res.data.getString("activityRule"));
        Assert.assertNotNull(final_res.data.getString("isReceive"));
        Assert.assertNotNull(final_res.data.getString("newDisciple"));
        Assert.assertNotNull(final_res.data.getString("effectiveDisciple"));
        Assert.assertNotNull(final_res.data.getString("forecastMoney"));
        Assert.assertNotNull(final_res.data.getString("newDiscipleList"));
        Assert.assertNotNull(final_res.data.getString("effectiveDiscipleList"));        
        Assert.assertNotNull(final_res.data.getJSONObject("shareInfo").getString("title"));
        Assert.assertNotNull(final_res.data.getJSONObject("shareInfo").getString("desc"));
        Assert.assertNotNull(final_res.data.getJSONObject("shareInfo").getString("img"));
        Assert.assertNotNull(final_res.data.getJSONObject("shareInfo").getString("url"));	
    }
	
	@Test(dataProvider="exfalse", priority=2, enabled=true)
	public void activityNewYearFalseTest(String uid, String act_id, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
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


