package com.bdtt.rest.app.news;

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
 * 订阅列表（分男女）（5.4版本赚钱阅读新加入）
 */

public class MediaListSexTest {
	
	String testUrl="media/listSex";
	String baseUrl=PropertiesHandle.readValue("newsbaseUrl");
	
	String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws Exception{
    	return new Object[][]{
            {uid, "1", "success"},
            {"", "1", "success"},
    		{GetMySql.getUserId("13066666666"), "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("订阅列表（分男女）接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("订阅列表（分男女）接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String uid, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);

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


