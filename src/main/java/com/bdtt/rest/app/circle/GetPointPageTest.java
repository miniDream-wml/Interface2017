package com.bdtt.rest.app.circle;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bdtt.rest.util.GetCircleFeedId;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.MD5Util;
import com.bdtt.rest.util.NewFeedCreation;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * 
 * @author mouhaiyan
 * 删除动态接口测试
 * 5.3之前的删除动态接口，不会判断是否是管理员账号
 *
 */
public class GetPointPageTest {
	String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	String delFeedUrl="admin/getPointPage";
	String uid=PropertiesHandle.readValue("uid");
	static String feed_id;
	static String comment_id;

	@BeforeClass
	public void setUp() throws Exception {
		System.out.println("删除动态接口测试开始！！");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		//System.out.println(MD5Util.MD5(df.format(new Date())));
		feed_id = NewFeedCreation.createNewFeedId(MD5Util.MD5(df.format(new Date()))); //创建一天新的动态，并获取它的feed_id
		NewFeedCreation.createNewAdmire(feed_id); //对新创建的动态点赞
		comment_id = NewFeedCreation.createNewCommentId(feed_id);//对新创建的动态进行评论，并获取评论的comment_id	
	}
	
	@DataProvider(name="delfeed")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
		return new Object[][] {
			{uid, GetCircleFeedId.getCircleFeedId(2), "1", GetCircleFeedId.getCircleFeedUid(2), "1","success"},//获取第三条动态的积分奖励管理页面，管理员
			{uid, feed_id, "1", uid, "1","success"},//获取新创建动态的积分奖励管理页面，管理员
			{uid, comment_id, "2", uid, "1","success"},//获取新创建动态的评论的积分奖励管理页面，管理员
		};
	}

	
	@Test(dataProvider="delfeed")
	public void delFeedTest(String uid, String target_id, String type, String rewarded_uid, String excode, String exmsg) {
		String url=baseUrl+delFeedUrl+UrlAdd.UrlAdd();
		 Map<String, String> params=ParamsBuilt.params_built();
		 params.put("uid", uid);
		 params.put("target_id", target_id); //被奖励的动态或评论ID
		 params.put("type", type); //被奖励的类型，1表示动态，2表示评论
		 params.put("rewarded_uid", rewarded_uid); //被奖励的动态或评论的UID
		 
		 String result=HTTPPost.httpPostForm(url, params);
		 System.out.println(result);
		 Gson gson=new Gson();
		 TestJSonResult final_result=gson.fromJson(result,TestJSonResult.class);
		 
		 int code=Integer.parseInt(excode);
			
		Assert.assertEquals(final_result.code, code);
		Assert.assertEquals(final_result.msg, exmsg);
	}

	@AfterClass
	public void tearDown() throws Exception {
		System.out.println("删除动态接口测试结束！！");
		NewFeedCreation.deleteNewFeed(feed_id); //删除该动态
	}
	
}
