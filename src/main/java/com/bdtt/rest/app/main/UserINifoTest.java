package com.bdtt.rest.app.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONObject;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.RedisOperate;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * 获取用户信息接口
 * @author mouhaiyan
 *
 */
public class UserINifoTest {
	String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="extrue")
	public Object[][] ParameterTrue(){
    	return new Object[][]{
        	{uid, "1", "操作成功"},//这个接口返回的不是success
        }; 
	}
	
	@DataProvider(name="exfalse")
	public Object[][] ParameterFalse(){
    	return new Object[][]{
        	{uid, "1", "操作成功"},//这个接口返回的不是success
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("获取用户信息接口测试开始！");
		//从redis删除对应的key
		String udid = PropertiesHandle.readValue("udid");
		String executecommand = "/home/tangqiliang/Scripts/operateredis.sh 'del alert:life_service:" + udid + "'";
		System.out.println(executecommand);
		RedisOperate.exec("101.37.228.23", "root", "q!xXMHjBmXTq8QMW", null, executecommand);
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("获取用户信息接口测试结束！");
	}
	
	@Test(dataProvider="extrue", priority=1)
	public void getUserInfoTest(String uId , String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("appbaseUrl") + "appuser/userinfo" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("uid", uId);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code, code);
        Assert.assertEquals(final_res.msg, exmsg);
        
        JSONObject data = final_res.data;
        Assert.assertNotNull(data.getString("behavior_count"));//5.3加入该字段
        Assert.assertNotNull(data.getJSONObject("alert").getString("img"));//5.5加入该字段        
        Assert.assertNotNull(data.getJSONObject("alert").getString("url"));//5.5加入该字段        
	}	
	
	@Test(dataProvider="exfalse", priority=2)
	public void getUserInfoFalseTest(String uId , String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("appbaseUrl") + "appuser/userinfo" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("uid", uId);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code, code);
        Assert.assertEquals(final_res.msg, exmsg);
        
        JSONObject data = final_res.data;
        Assert.assertNotNull(data.getString("behavior_count"));//5.3加入该字段
        Assert.assertEquals(data.getJSONObject("alert"), new JSONObject());//5.5加入该字段，这个时候返回的是空的json对象：{}
	}	
	
}
