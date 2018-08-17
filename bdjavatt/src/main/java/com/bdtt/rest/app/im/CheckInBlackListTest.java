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
 * 判断用户是否在对方的黑名单
 * @author mouhaiyan
 *
 */
public class CheckInBlackListTest {

	String accid=GetAccids.getAccidResult();
	String targetAccid=GetAccids.getTargetAccid();
	
	@DataProvider(name="black")
	public Object[][] Parameter(){
    	return new Object[][]{
        	{accid,targetAccid, "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println(" 是否在黑名单接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("是否在黑名单接口测试结束！");
	}
	
	@Test(dataProvider="black")
	public void inBlackTest(String accID,String targetAccId, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    		String url = PropertiesHandle.readValue("imbaseUrl") + "im/checkInBlacklist" + UrlAdd.UrlAdd();
        System.out.println("url:"+url);
        Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("accid", accID);
        params.put("target_accid", targetAccId);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}	
	
}


