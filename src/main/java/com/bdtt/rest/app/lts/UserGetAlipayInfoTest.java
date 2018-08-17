package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.RSAEncrypt;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;

import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by tangqiliang on 2017/12/25.
 * 获取支付宝信息（5.4版本赚钱阅读新加入）
 */

public class UserGetAlipayInfoTest {
	
	String testUrl="user/getAlipayInfo";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String uid=PropertiesHandle.readValue("uid");
	String nsign;//uid rsa 加密之后base64编码
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws Exception{
    	return new Object[][]{
            {GetMySql.getUserId("13011111111"), RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "1", ""},//这个接口返回正确的时候没有success信息
            {uid, RSAEncrypt.encriptUID(uid), "1", ""},//返回空的account和name；理论上应该返回该用户支付宝账号没绑定
            {uid, "", "0", "该请求不合法!"},//    
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("获取支付宝信息接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("获取支付宝信息接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String uid, String nsign, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("nsign", URLEncoder.encode(nsign,"UTF-8"));//解决nsign中特殊字符的问题

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


