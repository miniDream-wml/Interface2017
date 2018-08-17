package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;

import net.sf.json.JSONObject;

import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by tangqiliang on 2018/01/18.
 * 服务器时间校验
 */

public class CheckTimeTest {
	
	String testUrl="lts/checkTime";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	
	//String uid=PropertiesHandle.readValue("uid");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{"1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		System.out.println("服务器时间校验接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("服务器时间校验接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException, ParseException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
        
        JSONObject data = final_res.data;
        String realTime = data.getString("realTime");
        String unixTime = new BigDecimal(data.getString("unixTime")).toString();//为防止data.getString("unixTime")返回的是科学计数法样子的string
        //System.out.println(unixTime);

        //Assert.assertEquals(realTime, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//验证返回的是当前系统时间
        Assert.assertTrue(realTime.contains(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())));

        //把系统时间转换成时间戳
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(realTime);
        long ts = date.getTime()/1000;
        String res = String.valueOf(ts);
        
        Assert.assertEquals(unixTime, res);//验证返回的时间戳正确

	}	
}


