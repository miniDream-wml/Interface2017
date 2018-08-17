package com.bdtt.rest.app.account;

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
 * 注册账号登录
 */

public class LoginTest {
	
	String testUrl="account/login";
	String baseUrl=PropertiesHandle.readValue("accountbaseUrl");
	
	String client_id="bdttapp";
	String client_secret="10740fa78ba59d9302ff5c29a3e5616c";
	String grant_type="password";
	String username="13699999999";
	String password="123456";
	
	@DataProvider(name="ex")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{client_id, client_secret, grant_type, username, password, "1", "success"}
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("注册账号登录接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("注册账号登录接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String client_id, String client_secret, String grant_type, String username, String password, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        //System.out.println(url);
        
    	Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("client_id", client_id);
        params.put("client_secret", client_secret);
        params.put("grant_type", grant_type);
        params.put("username", username);
        params.put("password", password);
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


