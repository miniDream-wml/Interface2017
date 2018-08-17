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
 * 切换城市接口
 * @author mouhaiyan
 *
 */
public class ChangeCityTest {

	String uid=PropertiesHandle.readValue("uid");
	String udid=PropertiesHandle.readValue("udid");
	String region_id=PropertiesHandle.readValue("region_id");
	
	@DataProvider(name="change")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{uid,udid,region_id, "1", "success"},//登录用户切换城市
        	{"",udid,region_id, "1", "success"},//未登录用户切换城市
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("切换城市接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("切换城市接口测试结束！");
	}
	
	@Test(dataProvider="change")
	public void changeCityTest(String uId ,String udid,String regionID, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("appbaseUrl") + "Appregion/changeCity" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("uid", uId);
        params.put("udid", udid);
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
