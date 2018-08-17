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

import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * 专题列表页接口
 * @author mouhaiyan
 *
 */

public class GetTopicListTest {
	
	@DataProvider(name="tp")
	public Object[][] Parameter() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
    	return new Object[][]{
        	{GetMySql.getTopicID(), "","1", "success"},
        	{GetMySql.getdeleteTopicID(), "","1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("专题列表页接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("专题列表页接口测试结束！");
	}
	
	@Test(dataProvider="tp")
	public void getNewsInfomationTest(String news_id, String index,String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("newsbaseUrl") + "index/gettopiclist" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("news_id", news_id);
        params.put("index", index);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}	
	
}

