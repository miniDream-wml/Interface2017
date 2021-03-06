package com.bdtt.rest.app.circle;

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
 * Created by tangqiliang on 2017/11/23.
 * 积分明细
 */

public class GetUserPointDetailTest {
	
	String testUrl="Circle/getUserPointDetail";
	String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	
	String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{uid, "1", "1", "success"},
        	{uid, "2", "1", "success"},
        	{uid, "3", "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("积分明细接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("积分明细接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String uid, String page, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("page", page);//分页，但实际上为了兼容奇涵那边的H5，返回的结果并没有真的分页，是有多少条返回多少条；返回的结果中也会有一个page值，数字比传入的大1

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


