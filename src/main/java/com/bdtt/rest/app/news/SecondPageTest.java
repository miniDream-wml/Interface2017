package com.bdtt.rest.app.news;

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
 * Created by tangqiliang on 2017/9/13.
 * 资讯二级页面
 */

public class SecondPageTest {
	
	String testUrl="index/more";
	String baseUrl=PropertiesHandle.readValue("newsbaseUrl");
	
	String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{"1", "2", "1", "1", "1", "success"},
        	{"2", "2", "1", "1", "1", "success"},
        	//{"", GetCircleFeedId.getCircleFeedId(), "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("资讯二级页面接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("资讯二级页面接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String channel_id, String mode, String index, String lasttime, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        //params.put("uid", uid);
        params.put("channel_id", channel_id);
        params.put("mode", mode);
        params.put("index", index);
        params.put("lasttime", lasttime);

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


