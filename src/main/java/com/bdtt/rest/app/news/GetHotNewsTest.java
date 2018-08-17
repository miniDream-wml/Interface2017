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
 * Created by tangqiliang on 2017/8/8.
 * 一周热文/历史热文/历史爆料
 */

public class GetHotNewsTest {
	
	String testUrl="promotion/getHotNews";
	String baseUrl=PropertiesHandle.readValue("newsbaseUrl");
	String region_id=PropertiesHandle.readValue("region_id");
	
	@DataProvider(name="ex")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{region_id,"1", "1", "success"},
        	{region_id,"2", "1", "success"},
        	{region_id,"3", "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("一周热文/历史热文/历史爆料接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("一周热文/历史热文/历史爆料接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String regionID,String type, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        //System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("type", type);
        params.put("region_id", regionID);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}	
}
