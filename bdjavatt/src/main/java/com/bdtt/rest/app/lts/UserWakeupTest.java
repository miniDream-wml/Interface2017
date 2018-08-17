package com.bdtt.rest.app.lts;

import com.google.gson.Gson;
import com.bdtt.rest.util.GetMySql;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.MySqlTest;
import com.bdtt.rest.util.ParamsBuilt;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;

import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by tangqiliang on 2018/01/30.
 * 唤醒徒弟（5.5版本赚钱阅读二期新加入）
 * parent_id是师傅的uid；要成功唤醒需满足两个条件：1、local_money_wakeup中该徒弟的“wakeup”字段需要为0（唤醒后变成为1）；2、local_money_wakeup_record中该徒弟的create_time需要为当前时间的7天内
 */

public class UserWakeupTest {
	
	String testUrl="user/wakeup";
	String baseUrl=PropertiesHandle.readValue("ltsbaseUrl");
	String parent_id=PropertiesHandle.readValue("uid");//师傅的uid
	String uid;
	
	@DataProvider(name="extrue")
	public Object[][] ParameterTrue() throws Exception{
    	return new Object[][]{
            {parent_id, GetMySql.getUserId("13000000001"), "1", "success"},
            {parent_id, GetMySql.getUserId("13000000002"), "1", "success"},
            {parent_id, GetMySql.getUserId("13000000003"), "1", "success"},
            {parent_id, GetMySql.getUserId("13000000004"), "1", "success"},
            {parent_id, GetMySql.getUserId("13000000005"), "1", "success"},
        }; 
	}
	
	@DataProvider(name="exfalse")
	public Object[][] ParameterFalse() throws Exception{
    	return new Object[][]{
            {parent_id, GetMySql.getUserId("13000000001"), "0", "该用户暂时不能被唤醒!"},//7天内被唤醒过
            {"1111111", GetMySql.getUserId("13000000001"), "0", "师徒信息校验失败!"},//错误的师徒关系
            {"", GetMySql.getUserId("13000000001"), "0", "师傅ID不能为空!"},//师傅为空
            {parent_id, "", "0", "请先登录"},//徒弟为空
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		System.out.println("唤醒徒弟接口测试开始！");
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("唤醒徒弟接口测试结束！");
	}
	
	@Test(dataProvider="extrue", priority=1, enabled=true)
	public void userWakeupTest(String parent_id, String uid, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        MySqlTest.update_user_active_time(uid);//变更local_money_wakeup中的wakeup字段，设置为0，满足第一个唤醒条件
        MySqlTest.delete_user_wakeup_record(uid);//删除历史唤醒记录，确保满足第二个唤醒条件
        int parent_before_gold_number = Integer.parseInt(GetMySql.getGoldNum(parent_id));//获取师傅唤醒前的金币数
        int subordination_before_gold_number = Integer.parseInt(GetMySql.getGoldNum(uid));//获取徒弟唤醒前的金币数
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("parent_id", parent_id);
        params.put("uid", uid);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int coderesult = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code, coderesult);
        Assert.assertEquals(final_res.msg, exmsg);
        Assert.assertEquals(final_res.data.getString("desc"), "您成功被唤醒，获得金币奖励！");
        
        int parent_after_gold_number = Integer.parseInt(GetMySql.getGoldNum(parent_id));//获取师傅唤醒后的金币数
        int subordination_after_gold_number = Integer.parseInt(GetMySql.getGoldNum(uid));//获取徒弟唤醒后的金币数
        Assert.assertEquals(parent_before_gold_number+300, parent_after_gold_number);//唤醒后师傅奖励300金币
        Assert.assertEquals(subordination_before_gold_number+300, subordination_after_gold_number);//唤醒后徒弟奖励300金币
	}	
	
	@Test(dataProvider="exfalse", priority=2, enabled=true)
	public void userWakeupFalseTest(String parent_id, String uid, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        Map<String, String> params = ParamsBuilt.params_built();
        params.put("parent_id", parent_id);
        params.put("uid", uid);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int coderesult = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code, coderesult);
        Assert.assertEquals(final_res.msg, exmsg);

	}	
}


