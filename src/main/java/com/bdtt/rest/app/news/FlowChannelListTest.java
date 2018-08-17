package com.bdtt.rest.app.news;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
 * Created by tangqiliang on 2017/12/18.
 * 首页栏目（频道）列表(5.3.5版本及之后）；根据用户选择展示频道列表顺序
 */

public class FlowChannelListTest {
	
	String testUrl="flow/channelList";
	String baseUrl=PropertiesHandle.readValue("newsbaseUrl");
	String uid=PropertiesHandle.readValue("uid");
		
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{uid, "1", "success"},
        	{"", "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("首页栏目（频道）列表接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("首页栏目（频道）列表接口测试结束！");
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
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
        
        JSONObject data = final_res.data;
        JSONArray channellist = data.getJSONArray("channel");
        Assert.assertEquals(channellist.size(), 19); //验证返回19个频道
        
        //验证返回的第一个频道是“推荐”，第二个频道是“本地”
        JSONObject firstchannel = channellist.getJSONObject(0);
        Assert.assertEquals(firstchannel.getInt("id"), 1);
        Assert.assertEquals(firstchannel.getString("name"), "推荐");
        JSONObject secondchannel = channellist.getJSONObject(1);
        Assert.assertEquals(secondchannel.getInt("id"), 2);
        Assert.assertEquals(secondchannel.getString("name"), "本地");
	}	
}

