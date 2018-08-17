package com.bdtt.rest.app.news;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetCircleFeedId;
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
 * 同步机器的订阅信息到用户的接口, 对于从来没有过订阅行为的用户，将机器的订阅信息和他进行绑定
 */

public class SynTest {
	
	String testUrl="media/syn";
	String baseUrl=PropertiesHandle.readValue("newsbaseUrl");
	
	String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{"uid", "1", "success"},
        	//{GetCircleFeedId.getCircleFeedId(), "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("同步机器的订阅信息到用户的接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("同步机器的订阅信息到用户的接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String uid, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);

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


