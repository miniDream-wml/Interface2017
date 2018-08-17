package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.MySqlTest;
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
 * 提交提现（5.4版本赚钱阅读新加入）
 */

public class UserWithdrawTest {
	
	String testUrl="user/withdraw";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String uid=PropertiesHandle.readValue("uid");
	String num;//提现金额
	String nsign;//uid rsa 加密之后base64编码
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws Exception{
    	return new Object[][]{
            {GetMySql.getUserId("13011111111"), "1", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "1", "success"},//一元提现，如果已经一天提现过，则code返回0，并且提示“该用户已经提取过!”
            {GetMySql.getUserId("13011111111"), "1", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "0", "该用户已经提取过!"},//一元提现，如果已经一天提现过，则code返回0，并且提示“该用户已经提取过!”
            {GetMySql.getUserId("13011111111"), "30", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "1", "success"},//提现30
            {GetMySql.getUserId("13011111111"), "50", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "1", "success"},//提现50
            {GetMySql.getUserId("13011111111"), "100", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "1", "success"},//提现100
            {GetMySql.getUserId("13011111111"), "10", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "0", "提现额度不是指定的额度,不能提现!"},//提现10，不是配置的提现金额
            {GetMySql.getUserId("13011111111"), "30", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "0", "用户余额不足,不能体现!"},//msg应该提示余额不足，而不是返回success；data应该为{}, code为0
            {GetMySql.getUserId("13011111111"), "0.1", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "0", "提现额度不是指定的额度,不能提现!"},//应该提示余额不足
            {GetMySql.getUserId("13011111111"), "0", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "0", "0元不能提现!"},//提现金额不能为0
            {GetMySql.getUserId("13011111111"), "-1", RSAEncrypt.encriptUID(GetMySql.getUserId("13011111111")), "0", "提现额度不是指定的额度,不能提现!"},//提现金额不能为负数
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		System.out.println("提交提现接口测试开始！");
		MySqlTest.update_user_gold_money(GetMySql.getUserId("13011111111"), "10000", "20000");//给用户金币和零钱
		MySqlTest.delete_user_yytx(GetMySql.getUserId("13011111111"));//删除用户一元提现记录
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("提交提现接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String uid, String num, String nsign, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("num", num);
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


