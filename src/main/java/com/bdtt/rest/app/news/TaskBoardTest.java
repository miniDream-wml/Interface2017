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

import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * 任务榜单接口（5.4新接口，服务器还没开始开发）
 * @author mouhaiyan
 *
 */

public class TaskBoardTest {
	String region_id=PropertiesHandle.readValue("region_id");
	
	@DataProvider(name="tb")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{region_id, "1", "success"},
        	{"112111", "1", "success"},//兴化
        	{"401011", "1", "success"},//林周
        	{"101010", "1", "success"},//滨江
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("任务榜单接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("任务榜单接口测试结束！");
	}
	
	@Test(dataProvider="tb")
	public void gettashBoardTest(String regionID, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = PropertiesHandle.readValue("newsbaseUrl") + "index/taskBoard" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("region_id", regionID);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}	
	
}
