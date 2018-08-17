package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.MySqlTest;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;

import net.sf.json.JSONObject;

import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by tangqiliang on 2017/12/25.
 * 任务中心（5.4版本赚钱阅读新加入）
 */

public class TaskListTest {
	
	String testUrl="task/list";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{uid, "1", "success"},
        	{GetMySql.getUserId("13066666666"), "1", "success"},
        	{GetMySql.getUserId("13000000000"), "1", "success"},//有新手任务
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		System.out.println("任务中心接口测试开始！");
		MySqlTest.update_user_create_time(GetMySql.getUserId("13000000000"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("任务中心接口测试结束！");
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
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code, code);
        Assert.assertEquals(final_res.msg, exmsg);
        
        JSONObject data = final_res.data;
        Assert.assertEquals(data.getJSONObject("extra").getJSONObject("user").getString("uid"), uid);
        Assert.assertEquals(data.getJSONObject("extra").getJSONObject("user").getInt("yytx"), 1);
		
	}	
}


