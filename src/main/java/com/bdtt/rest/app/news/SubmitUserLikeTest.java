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

import com.bdtt.rest.util.GetMediaID;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * @author mouhaiyan
 * 上传用户定制频道和公众号内容
 *
 */
public class SubmitUserLikeTest {
	String uid=PropertiesHandle.readValue("uid");
	String region_like=GetMediaID.getMediaID().get(1).toString();
	String tag_like=GetMediaID.getMediaID().get(2).toString();
	String regionStr=region_like.substring(1, region_like.length()-1);
	String tagStr=tag_like.substring(1, tag_like.length()-1);
	
	@DataProvider(name="sm")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{uid,regionStr,tagStr, "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("上传用户订阅纪录接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("上传用户订阅纪录接口测试结束！");
	}
	
	@Test(dataProvider="sm")
	public void subMediaTest(String uId, String regionLike,String tagLike,String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("newsbaseUrl") + "media/userlike" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("uid", uId);
        params.put("region_like", regionLike);
        params.put("tag_like",tagLike );

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}	
	
}

