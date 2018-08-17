package com.bdtt.rest.app.main;

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
 * 打开app调用的接口（主要是该站点的爆料号，本地圈模式，详情页广告）
 * @author mouhaiyan
 *
 */

public class OpenAppTest {
	String region_id=PropertiesHandle.readValue("region_id");
	String uid=PropertiesHandle.readValue("uid");
	String udid=PropertiesHandle.readValue("udid");
	
	@DataProvider(name="op")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{region_id,uid,udid, "1", "success"},
        	{region_id,"",udid, "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("开启app接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("开启app接口测试结束！");
	}
	
	@Test(dataProvider="op")
	public void openAppTest(String regionID,String uId,String udId, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("appbaseUrl") + "App/openApp" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("uid", uId);
        params.put("udid", udId);
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
