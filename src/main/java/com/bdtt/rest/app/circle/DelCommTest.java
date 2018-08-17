package com.bdtt.rest.app.circle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bdtt.rest.util.GetCircleFeedId;
import com.bdtt.rest.util.GetFeedCommId;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * 
 * @author mouhaiyan
 * 删除动态的评论
 * 5.3之前，不判断是否是管理员账号
 */

public class DelCommTest {
 
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String delCommUrl="Circle/delComment";
	String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="delcomm")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
		return new Object[][] {
			{GetCircleFeedId.getCircleFeedId(0), uid, GetFeedCommId.getFeedCommId(), "1", "success"},//删除首条动态的首条评论
			{GetCircleFeedId.getCircleFeedId(0), "", GetFeedCommId.getFeedCommId(), "1", "success"},//未登录用户删除评论
		};
	}
	
	@Test(dataProvider="delcomm")
	public void delCommTest(String feed_id, String uId, String commID, String excode, String exmsg) {
		String url=baseUrl+delCommUrl+UrlAdd.UrlAdd();
		Map<String, String> params=ParamsBuilt.params_built();
		params.put("uid", uId);
		params.put("feed_id", feed_id);
		params.put("comment_id", commID);
		
		String result=HTTPPost.httpPostForm(url, params);
		System.out.println(result);
		
		int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
	}
	
	@BeforeClass
	public void setUp() throws Exception {
		System.out.println("删除动态首条评论测试开始！");
	}
	
	@AfterClass
	public void tearDown() throws Exception {
		System.out.println("删除动态首条评论测试结束！");
	}
}
