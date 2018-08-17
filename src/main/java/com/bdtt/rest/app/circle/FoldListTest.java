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
 * Created by tangqiliang on 2017/11/24.
 * 动态折叠
 */

public class FoldListTest {
	
	String testUrl="circle/foldList";
	String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	
	String uid=PropertiesHandle.readValue("uid");
	//String token=PropertiesHandle.readValue("token");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{"20171122", "0", "", "1", "success"},
        	{"20171122", "1", "", "1", "success"},
        	//{"", GetCircleFeedId.getCircleFeedId(0), "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("动态折叠接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("动态折叠接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String date, String type, String index, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("date", date);
        params.put("type", type);//0-本地，1-全国
        params.put("index", index);//首次传递空值,之后传递服务端返回的值

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


