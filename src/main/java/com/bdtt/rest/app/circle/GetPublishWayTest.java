package com.bdtt.rest.app.circle;

import com.google.gson.Gson;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;

import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangqiliang on 2017/8/8.
 * Modified by tangqiliang on 2017/11/22, 5.3.0 version
 * 获取发布方式
 */

public class GetPublishWayTest {
	
	String testUrl="Circle/getPublishWay";
	String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	
	String region_id=PropertiesHandle.readValue("region_id");
	
	@DataProvider(name="ex")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{region_id, "5.3.0", "1", "success"},
        	{region_id, "5.2.0", "1", "success"},
        	{region_id, "5.1.5", "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("获取发布方式接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("获取发布方式接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String region_id, String version, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        //Map<String, String> params = ParamsBuilt.params_built(); 
        Map<String, String> params = new HashMap<String, String>();
        params.put("region_id", region_id);
        params.put("version", version);

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
