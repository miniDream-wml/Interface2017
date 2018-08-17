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
 * Created by tangqiliang on 2017/11/23.
 * 更多话题
 */

public class GetMoreTopicTest {
	
	String testUrl="Circle/getMoreTopic";
	String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	
	//String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{"", "1", "1", "success"},
        	{"", "0", "1", "success"},
        	{"1", "1", "1", "success"},
        	{"1", "0", "1", "success"},
        	{"2", "1", "1", "success"},
        	{"2", "0", "1", "success"},        	
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("更多话题接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("更多话题接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String index, String circle_app_mode, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("index", index);
        params.put("circle_app_mode", circle_app_mode);//话题模式  全国1 本地0

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


