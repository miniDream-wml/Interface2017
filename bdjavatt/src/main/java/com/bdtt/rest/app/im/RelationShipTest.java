package com.bdtt.rest.app.im;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bdtt.rest.util.GetAccids;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;
import com.google.gson.Gson;

/**
 * 拉黑用户或者取消加入黑名单
 * @author mouhaiyan
 *
 */

public class RelationShipTest {
	String accid=GetAccids.getAccidResult();
	String targetAccid=GetAccids.getTargetAccid();
	
	@DataProvider(name="isblack")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{accid,targetAccid, "1","1","1", "success"},//加入黑名单
        	{accid,targetAccid, "1","0","1", "success"},//取消黑名单
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println(" 加入／取消加入黑名单接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("加入／取消加入黑名单接口测试结束！");
	}
	
	@Test(dataProvider="isblack")
	public void isBlackTest(String accID,String targetAccId, String type,String value,String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("imbaseUrl") + "im/relationship" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("accid", accID);
        params.put("target_accid", targetAccId);
        params.put("type", type);
        params.put("value", value);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}	
	
}



