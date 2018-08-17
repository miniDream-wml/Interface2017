package com.bdtt.rest.app.circle;

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
 * Created by tangqiliang on 2017/11/24.
 * 后台删除积分
 */

public class DelPointTest {
	
	String testUrl="Circle/delPoint";
	String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	String uid=PropertiesHandle.readValue("uid");
		
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
    		{uid, "2", "Ryan兑换扣分接口测试", "5", "1", "success"},
    		{uid, "2", "Ryan惩罚扣除接口测试", "12", "1", "success"},
        	{GetMySql.getUserId("13066666666"), "2", "test1306兑换扣分接口测试", "5", "1", "success"},
        	{GetMySql.getUserId("13066666666"), "2", "test1306惩罚扣除接口测试", "12", "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("后台删除积分接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("后台删除积分接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String uid, String point, String content, String type, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("point", point);//扣除的积分
        params.put("content", content); //扣除原因
        params.put("type", type);//5为兑换,12为惩罚扣除

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



