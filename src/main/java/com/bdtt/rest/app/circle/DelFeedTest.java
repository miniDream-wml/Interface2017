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
 * 
 * @author mouhaiyan
 * 删除动态接口测试
 * 5.3之前的删除动态接口，不会判断是否是管理员账号
 *
 */
public class DelFeedTest {
	String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	String delFeedUrl="Circle/delete";
	String uid=PropertiesHandle.readValue("uid");
	String token=PropertiesHandle.readValue("token");
	
	@DataProvider(name="delfeed")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
		return new Object[][] {
			{uid, token, GetCircleFeedId.getCircleFeedId(0), "1","success"},//管理员删除动态
			//{GetMySql.getUserId("13066666666"), GetToken.getToken("13066666666", "123456"), GetCircleFeedId.getCircleFeedId(0), "1", "success"},//普通用户删除别人的动态，删除成功，已反馈给服务端
		};
	}
	
	@Test(dataProvider="delfeed")
	public void delFeedTest(String uid, String token, String feed_id, String excode, String exmsg) {
		String url=baseUrl+delFeedUrl+UrlAdd.UrlAdd();
		 Map<String, String> params=ParamsBuilt.params_built();
		 params.put("uid", uid);
		 params.put("token", token);
		 params.put("feed_id", feed_id);
		 
		 String result=HTTPPost.httpPostForm(url, params);
		 System.out.println(result);
		 Gson gson=new Gson();
		 TestJSonResult final_result=gson.fromJson(result,TestJSonResult.class);
		 
		 int delcode=final_result.data.getInt("status");
		 if (delcode==1) {
			System.out.println("动态删除成功！");
		 }else {
			System.out.println("动态删除失败！");
		 }
		 
		 int code=Integer.parseInt(excode);
			
		Assert.assertEquals(final_result.code, code);
		Assert.assertEquals(final_result.msg, exmsg);
	}
	
	@BeforeClass
	public void setUp() throws Exception {
		System.out.println("删除动态接口测试开始！！");
	}
	
	@AfterClass
	public void tearDown() throws Exception {
		System.out.println("删除动态接口测试结束！！");
	}
	
}
