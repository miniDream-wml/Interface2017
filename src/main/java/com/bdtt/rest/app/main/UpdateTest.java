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

import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * App更新接口(android版本11，iOS版本10)
 * @author mouhaiyan
 *
 */

public class UpdateTest {
	
	@DataProvider(name="update")
	public Object[][] Parameter() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
    	return new Object[][]{
        	{"11","4.5.1","1", "success"},//android低版本的请求更新
        	{"11", GetMySql.getCurVersion("11"),"1", "success"},//android数据库获取最新版本的请求更新
        	{"10","4.5.1","1", "success"},//ios低版本的请求更新
        	{"10", GetMySql.getCurVersion("10"),"1", "success"},//ios数据库获取最新版本的请求更新
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("App更新接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("App更新接口测试结束！");
	}
	
	@Test(dataProvider="update")
	public void isUpdateTest(String equipment_type ,String version, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("appbaseUrl") + "app/update" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("equipment_type", equipment_type);
        params.put("version", version);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}	
	
}

