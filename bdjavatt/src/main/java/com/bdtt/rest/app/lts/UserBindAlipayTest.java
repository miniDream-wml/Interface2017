package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.MySqlTest;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.RSAEncrypt;
import com.bdtt.rest.util.RegisterUser;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;

import net.sf.json.JSONObject;

import org.testng.Assert;
import org.testng.annotations.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by tangqiliang on 2017/12/25.
 * 绑定支付宝（5.4版本赚钱阅读新加入）
 */

public class UserBindAlipayTest {
	
	String testUrl="user/bindAlipay";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String uid=PropertiesHandle.readValue("uid");
	String name;//用户姓名
	String account; //支付宝账号
	String nsign;//uid rsa 加密之后base64编码
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws Exception{
    	return new Object[][]{
            {GetMySql.getUserId("13011111111"), "InterfaceTest", "InterfaceTest@163.com", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "1", "success"},
            {GetMySql.getUserId("13011111111"), "InterfaceTest", "InterfaceTest@163.com", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "0", "支付信息已存在,不能重新绑定"},
            {uid, "RyanTestTest", "RyanTestTest@163.com", "", "0", "该请求不合法!"},
            //{"3000107", "RyanTestTest", "RyanTestTest@163.com", RSAEncrypt.encriptUID("3000107"), "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		System.out.println("绑定支付宝接口测试开始！");
		MySqlTest.delete_user(GetMySql.getUserId("13011111111"));//删除用户
		RegisterUser.registerUser("13011111111", "123456", "test");//新注册用户，参数为用户名，密码，以及环境，环境是test, pre或者online
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("绑定支付宝接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String uid, String name, String account, String nsign, String excode, String exmsg) throws Exception {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        //新建一个JSON对象存放用户姓名和支付宝账号，请求接口的时候，把该JSON对象进行RSA和base64加密后上传给服务器
        JSONObject jsonParam = new JSONObject();//新建一个json的格式对象
        jsonParam.put("name", name);
        jsonParam.put("account",account);
                
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("alipayinfo", URLEncoder.encode(RSAEncrypt.encriptAlipayinfo(jsonParam), "UTF-8"));//encode解决nsign中特殊字符的问题
        params.put("nsign", URLEncoder.encode(nsign, "UTF-8"));//encode解决nsign中特殊字符的问题

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


