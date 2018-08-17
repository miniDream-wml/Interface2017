package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetMySql;
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
 * 用户收徒信息（5.4版本赚钱阅读新加入）
 */

public class UserSubordinationInfoTest {
	
	String testUrl="user/subordinationInfo";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws Exception{
    	return new Object[][]{
            {uid, "1", "success", 5},
            {"1256918", "1", "success", 0},
    		{GetMySql.getUserId("13066666666"), "1", "success", 0},
    		{"", "1", "success", 0},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("用户收徒信息接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("用户收徒信息接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String uid, String excode, String exmsg, int count) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
        Assert.assertEquals(final_res.data.getInt("count"), count);
		
	}	
}


