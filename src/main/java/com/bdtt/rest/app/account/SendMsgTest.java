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
 * 发送验证码（5.4版本赚钱阅读新加入）
 */

public class SendMsgTest {
	
	String testUrl="account/sendMsg";
	String baseUrl=PropertiesHandle.readValue("accountbaseUrl");
	//String uid=PropertiesHandle.readValue("uid");
	String phone;
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws Exception{
    	return new Object[][]{
            {"13666680348", "1", "success"},
            {"13666680348", "0", "两次发送时间间隔不能小于1分钟"},
            {"", "0", "手机号格式不正确"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("发送验证码接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("发送验证码接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String phone, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("phone", phone);

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


