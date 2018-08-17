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
 * Created by tangqiliang on 2017/12/25.
 * 验证码验证（5.4版本赚钱阅读新加入）
 */

public class MsgAuthTest {
	
	String testUrl="account/auth";
	String baseUrl=PropertiesHandle.readValue("accountbaseUrl");
	//String uid=PropertiesHandle.readValue("uid");
	String phone;
	String code;
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws Exception{
    	return new Object[][]{
            {"13666680348", "348607", "1", "success"},
            {"13666680348", "111111", "0", "验证码已失效,请重新获取"},//错误的验证码
            {"", "", "0", "验证码已失效,请重新获取"},//验证码为空
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("验证码验证接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("验证码验证接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String phone, String code, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("phone", phone);
        params.put("code", code);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int coderesult = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code, coderesult);
        Assert.assertEquals(final_res.msg, exmsg);
		
	}	
}


