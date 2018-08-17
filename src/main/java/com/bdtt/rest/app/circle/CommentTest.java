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
import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.GetToken;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * @author mouhaiyan
 * 评论动态
 *
 */

public class CommentTest {
	String commUrl="Circle/comment";
	String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	String uid=PropertiesHandle.readValue("uid");
	String token=PropertiesHandle.readValue("token");
	//String been_uid="5461653"; //评论别人时被评论用户的ID，评论动态时beenUid为空
	
	@DataProvider(name ="comm")
	public Object [][]Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		return new Object[][] {
			//{uid, token, "5461653","回复别人动态",GetCircleFeedId.getCircleFeedId(0), "1", "success"},//回复别人动态
			{uid, token, "", "我是第一条评论", GetCircleFeedId.getCircleFeedId(0), "1", "success"},//用户Ryan评论动态
			{GetMySql.getUserId("13066666666"), GetToken.getToken("13066666666", "123456"), "", "我是第二条评论", GetCircleFeedId.getCircleFeedId(0), "1", "success"},//用户Test1306评论动态
         	{uid, token, "", "feedID为空的评论", "", "1", "success"},//feed—ID为空
         	{uid, "错误的token", "", "不成功的评论", GetCircleFeedId.getCircleFeedId(0), "15000", "亲，请登录呀~"},//错误的token，评论不成功
         	//{uid, token, "", "", GetCircleFeedId.getCircleFeedId(0), "1", "success"},//评论内容为空
		};	
	}

	@Test(dataProvider="comm")
	public void Commtest(String uid, String token, String been_uid, String content, String feed_Id, String exCode,String exMsg) {
		String url=baseUrl+commUrl+UrlAdd.UrlAdd();
		Map<String,String> params=ParamsBuilt.params_built();
		params.put("uid", uid); //评论用户uid
		params.put("token", token); //评论用户token
		params.put("been_uid", been_uid); //被回复评论的uid
		params.put("content", content); //评论内容
		params.put("feed_id", feed_Id); //被评论的动态
		
		String result=HTTPPost.httpPostForm(url, params);
		System.out.println(result);
		
		int code=Integer.parseInt(exCode);
		
		Gson gson=new Gson();
		TestJSonResult final_result=gson.fromJson(result,TestJSonResult.class);
		
		Assert.assertEquals(final_result.code, code);
		Assert.assertEquals(final_result.msg, exMsg);
	}
	
	@BeforeClass
	public void setUp() throws Exception {
		System.out.println("评论动态测试开始！");
	}
	
	@AfterClass
	public void tearDown() throws Exception {
		System.out.println("评论动态测试结束！");
	}

}
