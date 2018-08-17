package com.bdtt.rest.app.news;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * @author mouhaiyan
 * 订阅界面（媒体推荐和全国频道推荐）
 *
 */
public class MediaListTest {
	String uid=PropertiesHandle.readValue("uid");
	String region_id=PropertiesHandle.readValue("region_id");
	
	@DataProvider(name="ml")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{uid,region_id, "1", "success"},
        	{"",region_id, "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("订阅界面接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("订阅界面接口测试结束！");
	}
	
	@Test(dataProvider="ml")
	public void getMediaListTest(String uId, String regionId,String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("newsbaseUrl") + "media/list" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("uid", uId);
        params.put("region_id", regionId);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}	
	
}
