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
 * 用户奖励信息（5.4版本赚钱阅读新加入）
 */

public class UserRewardInfoTest {
	
	String testUrl="user/rewardInfo";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String uid=PropertiesHandle.readValue("uid");
	String nsign;// (uid rsa 加密之后base64编码)
	
	@DataProvider(name="extrue")
	public Object[][] ParameterTrue() throws Exception{
    	return new Object[][]{
            {GetMySql.getUserId("13000000000"), RSAEncrypt.encriptUID(GetMySql.getUserId("13000000000")), "1", "success"},
            {GetMySql.getUserId("13011111111"), RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "1", "success"},
        }; 
	}
	
	@DataProvider(name="exfalse")
	public Object[][] ParameterFalse() throws Exception{
    	return new Object[][]{
            {uid, "", "0", "该请求不合法!"},
            {"", "", "0", "该请求不合法!"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("用户奖励信息接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("用户奖励信息接口测试结束！");
	}
	
	@Test(dataProvider="extrue", priority=1)
	public void userRewardInfoTest(String uid, String nsign, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("nsign", URLEncoder.encode(nsign,"UTF-8"));//解决nsign中特殊字符的问题

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
        Assert.assertEquals(String.valueOf(final_res.data.getJSONObject("user_money").getInt("gold")), GetMySql.getGoldNum(uid));
        Assert.assertEquals(String.valueOf(final_res.data.getJSONObject("user_money").getJSONObject("change").getInt("now")), GetMySql.getMoneyNum(uid));
        Assert.assertEquals(String.valueOf(final_res.data.getJSONObject("user_money").getJSONObject("change").getInt("total")), GetMySql.getTotalMoneyNum(uid));
	}
	
	@Test(dataProvider="exfalse", priority=2)
	public void userRewardInfoFalseTest(String uid, String nsign, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("nsign", URLEncoder.encode(nsign,"UTF-8"));//解决nsign中特殊字符的问题

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


