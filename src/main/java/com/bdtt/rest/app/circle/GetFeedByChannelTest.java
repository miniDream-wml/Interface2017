package com.bdtt.rest.app.circle;

import com.google.gson.Gson;
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
 * Created by tangqiliang on 2017/8/9.
 * 获取本地圈动态
 */

public class GetFeedByChannelTest {
	
	String testUrl="Circle/getFeedByChannel";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	
	@DataProvider(name="ex")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{"0", "1", "1", "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("获取本地圈动态接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("获取本地圈动态接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String channel_id, String index, String mode, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        //System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("channel_id", channel_id);
        params.put("index", index);
        params.put("mode", mode);

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

