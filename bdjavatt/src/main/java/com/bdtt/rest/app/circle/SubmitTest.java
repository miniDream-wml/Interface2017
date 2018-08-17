package com.bdtt.rest.app.circle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
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
 * 
 * @author mouhaiyan
 * modified by tangqiliang on 2017/11/23
 * 上传动态的文字以及标签tags（动态发布）
 *
 */
public class SubmitTest {
	String baseUrl=PropertiesHandle.readValue("circlebaseUrl");
	String testUrl="circle/submit";
	String uid=PropertiesHandle.readValue("uid");
	String token = PropertiesHandle.readValue("token");
	//String regionname = GetMySql.getRegionName(PropertiesHandle.readValue("region_id"));
	String region_id = PropertiesHandle.readValue("region_id");
	String dedupl_token = "d6ac1d452cbee77b063ade5c97252641";
	String pic_infos="[{\"width\":640,\"path\":\"https:\\/\\/oss-cn-hangzhou.aliyuncs.com\\/bdtt-api\\/circle-image\\/150345314915664033871-C76D-4966-A166-25FF2D39441B.jpeg\",\"height\":1138},{\"width\":700,\"path\":\"https:\\/\\/oss-cn-hangzhou.aliyuncs.com\\/bdtt-api\\/circle-image\\/1503453149565053B079A-BF14-4E46-AF13-22774F0BCEC4.jpeg\",\"height\":1246},{\"width\":415,\"path\":\"https:\\/\\/oss-cn-hangzhou.aliyuncs.com\\/bdtt-api\\/circle-image\\/1503453149790674C52A8-0D8D-435A-AAB6-3C1213EDD352.jpeg\",\"height\":413}, {\"height\":\"160\",\"path\":\"http://oss-cn-hangzhou.aliyuncs.com/bdtt-api/circle-image/alioss_1511347637290.jpg?type\u003dgif\",\"width\":\"160\"}]";
	String video_info ="";
	//ArrayList <String>feed_list=new ArrayList<String>();
	
	@DataProvider(name="sub")
	public Object[][] Parameter() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
		String tag_names="["+"\""+GetMySql.getChannelName(PropertiesHandle.readValue("region_id"))+"\""+"]";	
		return new Object[][] {
			{uid, token, GetMySql.getRegionName(region_id), "接口测试动态发布", dedupl_token, tag_names, pic_infos, video_info, "1", "success"},//发布有文有图有地址有标签的动态
			//{ uid, token, "", "#接口测试#我要发布有图有文有话题有标签的动态", dedupl_token, tag_names, pic_infos, video_info, "1", "success"},//发布有文有图无地址有标签的动态
			//{ uid, token, "", "#接口测试#我要发布纯文字的动态","","","1", "success"},//发布纯文字动态
			//{ uid, token, "",tagnames,"","1", "success"},//无文字无图片时发布动态,可发布（需求来说不应发布成功）
			//{ "", token, region_id,"#接口测试#用户为空的动态",tagnames,picurls,"1", "success"},//用户为空时发布动态,接口请求失败
			//{ uid,"","#接口测试#region为空的动态",tagnames,picurls,"1", "success"},//region为空时发布动态，接口请求失败
			
		};
	}
	
	@Test(dataProvider="sub")
	public void SubTest(String uid, String token, String address, String content, String dedupl_token, String tag_names, String pic_infos, String video_info, String excode,String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException {
		String url=baseUrl+testUrl+UrlAdd.UrlAdd();
		System.out.println(url);	
		
		Map<String, String>params=ParamsBuilt.params_built();
		params.put("uid", uid);
		params.put("token", token);
		params.put("address", address); //所在位置
		params.put("content", content); //动态文字
		params.put("dedupl_token", dedupl_token); //避免重复提交的token
		params.put("tag_names", tag_names); //标签名字，5.3后只能有一个
		params.put("pic_infos", pic_infos); //图片列表
		params.put("video_info", video_info); //视频列表
		
		String result=HTTPPost.httpPostForm(url, params);
		
		System.out.println(result);
		
		Gson gson=new Gson();
		TestJSonResult final_result=gson.fromJson(result,TestJSonResult.class);
		
		String feedID=final_result.data.getString("feed_id");
		System.out.println("动态ID："+feedID);
	
		int code=Integer.parseInt(excode);
		
		Assert.assertEquals(final_result.code, code);
		Assert.assertEquals(final_result.msg, exmsg);
		
	}
	
	@BeforeClass
	public void setUp() throws Exception {
		System.out.println("发布动态接口测试开始！！");
	}
	
	@AfterClass
	public void tearDown() throws Exception {
		System.out.println("发布动态接口测试结束！！");
	}
}
