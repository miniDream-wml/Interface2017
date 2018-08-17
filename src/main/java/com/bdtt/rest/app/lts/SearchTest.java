package com.bdtt.rest.app.lts;

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
 * Created by tangqiliang on 2017/10/24.
 * 搜索（5.3版本加入）
 */

public class SearchTest {
	
	String testUrl="lts/search";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	
	//String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{"1", "你", "0", "1", "success"},
        	{"2", "你", "0", "1", "success"},
        	{"3", "你", "0", "1", "success"},
        	{"4", "你", "0", "1", "success"},
        	//{GetCircleFeedId.getCircleFeedId(), "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("搜索接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("搜索接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String type, String key, String offset, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("type", type);
        params.put("key", key);
        params.put("offset", offset);

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


