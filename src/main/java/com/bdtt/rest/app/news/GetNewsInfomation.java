package com.bdtt.rest.app.news;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bdtt.rest.util.GetNewsId;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * 
 * @author mouhaiyan
 * 新闻详情页下发
 *
 */

public class GetNewsInfomation {

	String newsID=GetNewsId.getNewsID();
	
	@DataProvider(name="ni")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{newsID, "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("新闻详情页下发接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("新闻详情页下发接口测试结束！");
	}
	
	@Test(dataProvider="ni")
	public void getNewsInfomationTest(String news_id, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("newsbaseUrl") + "News/getnewsInfomation" + UrlAdd.UrlAdd();
        
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("news_id", news_id);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}	
	
}
