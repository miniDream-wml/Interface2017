package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.MySqlTest;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.RegisterUser;
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
 * 收徒（5.4版本赚钱阅读新加入）
 * 实际上是拜师，参数uid是自己，code是师傅的code，扫了code后就拜师成功了
 */

public class UserSubordinateTest {
	
	String testUrl="user/subordinate";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String uid;//自己
	String code = "1" + PropertiesHandle.readValue("uid");//师傅的邀请码，生成规则为师傅的uid前加一个1
	
	@DataProvider(name="extrue")
	public Object[][] ParameterTrue() throws Exception{
    	return new Object[][]{
            {GetMySql.getUserId("13000000001"), code, "1", "success", 1, "拜师成功"},
            {GetMySql.getUserId("13000000002"), code, "1", "success", 1, "拜师成功"},
            {GetMySql.getUserId("13000000003"), code, "1", "success", 1, "拜师成功"},
            {GetMySql.getUserId("13000000004"), code, "1", "success", 1, "拜师成功"},
            {GetMySql.getUserId("13000000005"), code, "1", "success", 1, "拜师成功"},
        }; 
	}
	
	@DataProvider(name="exfalse")
	public Object[][] ParameterFalse() throws Exception{
    	return new Object[][]{
            {GetMySql.getUserId("13000000001"), code, "1", "success", 0, "该用户已绑定过,不能重复绑定!"},
            {GetMySql.getUserId("13000000001"), "", "1", "success", 0, "未输入邀请码"},
            {GetMySql.getUserId("13000000001"), "111", "1", "success", 0, "邀请者ID错误,请重新输入!"},
            {"", code, "1", "success", 0, "用户未登录"},
            {uid, "", "1", "success", 0, "用户未登录"},//有问题，不应该success
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		System.out.println("收徒接口测试开始！");
		MySqlTest.delete_user_subordination(PropertiesHandle.readValue("uid"));
		//创建徒弟账号
		for (int i=1; i<6; i++) {
			MySqlTest.delete_user(GetMySql.getUserId("1300000000"+i));//删除用户
			RegisterUser.registerUser("1300000000"+i, "123456", "test");//新注册用户，参数为用户名，密码，以及环境，环境是test, pre或者online
		}
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("收徒接口测试结束！");
	}
	
	@Test(dataProvider="extrue", priority=1)
	public void userSubordinateTest(String uid, String code, String excode, String exmsg, int status, String message) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("code", code);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int coderesult = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code, coderesult);
        Assert.assertEquals(final_res.msg, exmsg);
        Assert.assertEquals(final_res.data.getInt("status"), status);
        Assert.assertEquals(final_res.data.getString("message"), message);

	}	
	
	@Test(dataProvider="exfalse", priority=2)
	public void userSubordinateFalseTest(String uid, String code, String excode, String exmsg, int status, String message) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("code", code);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int coderesult = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code, coderesult);
        Assert.assertEquals(final_res.msg, exmsg);
        Assert.assertEquals(final_res.data.getInt("status"), status);
        Assert.assertEquals(final_res.data.getString("message"), message);

	}	
}


