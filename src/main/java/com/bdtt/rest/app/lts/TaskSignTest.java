package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.MySqlTest;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.RegisterUser;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;

import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by tangqiliang on 2017/12/25.
 * 签到（5.4版本赚钱阅读新加入）
 * 测试逻辑：新注册一个用户，然后改变用户的注册时间（create_time字段）来模拟每天的签到
 * 因为该接口设计到数据库表变动，所在只能在测试环境运行，预发和线上环境不能运行
 */

public class TaskSignTest {
	
	String testUrl="task/sign";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String uid=PropertiesHandle.readValue("uid");
	int gold_number; //要奖励的金币数
	int sign_day;//第几天签到
	
	@DataProvider(name="extrue")
	public Object[][] ParameterTrue() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{GetMySql.getUserId("13000000000"), "500", "1", "1", "success"},//第一天签到
        	{GetMySql.getUserId("13000000000"), "300", "2", "1", "success"},//第二天签到
        	{GetMySql.getUserId("13000000000"), "300", "3", "1", "success"},//第三天签到
        	{GetMySql.getUserId("13000000000"), "300", "4", "1", "success"},//第四天签到
        	{GetMySql.getUserId("13000000000"), "300", "5", "1", "success"},//第五天签到
        	{GetMySql.getUserId("13000000000"), "300", "6", "1", "success"},//第六天签到
        	{GetMySql.getUserId("13000000000"), "500", "7", "1", "success"},//第七天签到
        }; 
	}
	
	@DataProvider(name="exfalse")
	public Object[][] ParameterFalse() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
            {GetMySql.getUserId("13000000000"), "500", "8", "0", "签到天数和实际天数不一致!"},//第八天签到
    		{uid, "10", "1", "0", "签到活动只能新用户参加!"},//老用户签到
        	{GetMySql.getUserId("13000000000"), "500", "7", "0", "今日已签到,不能重复签到!"},//当天重复签到
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		System.out.println("签到接口测试开始！");
		MySqlTest.delete_user(GetMySql.getUserId("13000000000"));//删除用户
		RegisterUser.registerUser("13000000000", "123456", "test");//新注册用户，参数为用户名，密码，以及环境，环境是test, pre或者online
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("签到接口测试结束！");
	}
	
	@Test(dataProvider="extrue", priority=1)//priority代表优先级
	//@Test(enabled = false)//忽略这个测试
	public void taskSignTest(String uid, String gold_number, String sign_day, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("gold_number", gold_number);
        params.put("sign_day", sign_day);
        System.out.println(params);
         
        String updatedcreationtime = TaskSignTest.updateUserCreationTime(Integer.parseInt(sign_day)); //通过sign_day来计算出相应的用户注册时间
        MySqlTest.update_user_create_time(uid, updatedcreationtime);//修改数据库中用户的注册时间
        
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
        Assert.assertEquals(final_res.data.getString("sign_desc"), "成功签到，获得奖励！");//5.5加入sign_desc字段，显示“成功签到，获得奖励！”
        
        ArrayList<Integer> goldlist = new ArrayList<Integer>(Arrays.asList(500, 800, 1100, 1400, 1700, 2000, 2500));
        Assert.assertEquals(final_res.data.getJSONObject("reward").getInt("gold"), (int)goldlist.get(Integer.parseInt(sign_day)-1));//验证金币金额正确
	}
	
	@Test(dataProvider="exfalse", priority=2)
	//@Test(enabled = false)
	public void taskSignFalseTest(String uid, String gold_number, String sign_day, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("uid", uid);
        params.put("gold_number", gold_number);
        params.put("sign_day", sign_day);
        System.out.println(params);
                
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
		
	}
	
	//根据当前时间，来获取之前几天的时间，格式为yyyy-MM-dd HH:mm:ss
	public static String updateUserCreationTime (int i) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date=new Date(); //获取系统时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -(i-1));
        date = calendar.getTime();
        String updatedate = sdf.format(date);
        //System.out.println(updatedate);
        return updatedate;
	}
}


