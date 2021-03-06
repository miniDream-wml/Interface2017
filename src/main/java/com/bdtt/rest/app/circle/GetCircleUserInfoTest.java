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
 * Created by tangqiliang on 2017/8/21.
 * 获取本地圈个人主页用户相关信息
 */

public class GetCircleUserInfoTest {
	
	String testUrl="Circle/getCircleUserInfo";
	String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	String target_uid=PropertiesHandle.readValue("uid");
		
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
    		{target_uid, "1", "success"},
        	//{GetMySql.getUserId("13666666666"), uid, "1", "success"},
        	//{GetMySql.getUserId("0"), "1", "success"},
        	//{null, "1", "success"}
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("获取本地圈个人主页用户相关信息接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("获取本地圈个人主页用户相关信息接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String target_uid, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("target_uid", target_uid);//个人主页用户的uid，也是返回结果中的uid

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
        Assert.assertEquals(final_res.data.get("uid"), target_uid);
        Assert.assertEquals(final_res.data.get("region_name"), GetMySql.getRegionName(PropertiesHandle.readValue("region_id")));	
	}	
}



