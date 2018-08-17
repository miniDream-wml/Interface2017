package com.bdtt.rest.util;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * 
 * @author tangqiliang
 * created by tangqiliang on 2017/11/30
 * 新建动态，添加评论，点赞，删除动态
 *
 */
public class NewFeedCreation {
	
	static String baseUrl = PropertiesHandle.readValue("circlebaseUrl");
	static String uid = PropertiesHandle.readValue("uid");		
	static String token = PropertiesHandle.readValue("token");

	public static String createNewFeedId(String dedupl_token) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException {
		String testUrl="circle/submit";
		String region_id = PropertiesHandle.readValue("region_id");
		String address = GetMySql.getRegionName(region_id);
		String content = "接口测试动态发布";
		String tag_names="["+"\""+GetMySql.getChannelName(PropertiesHandle.readValue("region_id"))+"\""+"]";
		String pic_infos="[{\"width\":640,\"path\":\"https:\\/\\/oss-cn-hangzhou.aliyuncs.com\\/bdtt-api\\/circle-image\\/150345314915664033871-C76D-4966-A166-25FF2D39441B.jpeg\",\"height\":1138},{\"width\":700,\"path\":\"https:\\/\\/oss-cn-hangzhou.aliyuncs.com\\/bdtt-api\\/circle-image\\/1503453149565053B079A-BF14-4E46-AF13-22774F0BCEC4.jpeg\",\"height\":1246},{\"width\":415,\"path\":\"https:\\/\\/oss-cn-hangzhou.aliyuncs.com\\/bdtt-api\\/circle-image\\/1503453149790674C52A8-0D8D-435A-AAB6-3C1213EDD352.jpeg\",\"height\":413}, {\"height\":\"160\",\"path\":\"http://oss-cn-hangzhou.aliyuncs.com/bdtt-api/circle-image/alioss_1511347637290.jpg?type\u003dgif\",\"width\":\"160\"}]";
		String video_info ="";
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
		
		String feed_id = final_result.data.getString("feed_id");
		System.out.println(feed_id);
		return feed_id;		
	}
	
	public static void createNewAdmire(String feed_id) {
		String testUrl="Circle/admire";
		String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("token", token);
        params.put("feed_id", feed_id);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
	}
	
	public static String createNewCommentId(String feed_id) {
		String testUrl="Circle/comment";
		String url=baseUrl+testUrl+UrlAdd.UrlAdd();
		String content = "接口测试添加评论";
		Map<String,String> params=ParamsBuilt.params_built();
		params.put("uid", uid);
		params.put("token", token);
		params.put("content", content);
		params.put("feed_id", feed_id);
		
		String result=HTTPPost.httpPostForm(url, params);
		System.out.println(result);
		
		Gson gson=new Gson();
		TestJSonResult final_result=gson.fromJson(result,TestJSonResult.class);
        JSONObject dataResult = final_result.data;
        
        JSONArray comment_list = (JSONArray) dataResult.get("comments");
        JSONObject comment = comment_list.getJSONObject(0);
        String comm_id=comment.getString("comment_id");
        System.out.println(comm_id);
        
        return comm_id;
	}
	
	public static void deleteNewFeed(String feed_id) {
		String testUrl="admin/delFeed";
		String url=baseUrl+testUrl+UrlAdd.UrlAdd();
		Map<String, String> params=ParamsBuilt.params_built();
		params.put("uid", uid);
		params.put("token", token);
		params.put("feed_id", feed_id);
		 
		String result=HTTPPost.httpPostForm(url, params);
		System.out.println(result);
	}
	
	public static void main (String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClientProtocolException, SQLException, IOException {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    	//System.out.println(MD5Util.MD5(df.format(new Date())));

		String feed_id = NewFeedCreation.createNewFeedId(MD5Util.MD5(df.format(new Date())));
		NewFeedCreation.createNewAdmire(feed_id);
		NewFeedCreation.createNewCommentId(feed_id);
		NewFeedCreation.deleteNewFeed(feed_id);
	}
	
}
