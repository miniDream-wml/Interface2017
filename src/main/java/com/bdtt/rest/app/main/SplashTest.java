package com.bdtt.rest.app.main;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.MySqlTest;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;
import com.bdtt.rest.util.UrlAdd;

import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangqiliang on 2017/11/22.
 * 获取开屏广告及配置（5.3版本加入）
 * 因为涉及到数据库修改，正式环境不能用
 */

public class SplashTest {
	
	String testUrl="app/splash";
	String baseUrl=PropertiesHandle.readValue("appbaseUrl");
	
	//String uid=PropertiesHandle.readValue("uid");
	String region_id=PropertiesHandle.readValue("region_id");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
    	return new Object[][]{
        	{region_id, "1", "success"},
        }; 
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		System.out.println("获取开屏广告及配置接口测试开始！");
		MySqlTest.delete_row_from_Local_ad_switch("112111");//兴化
		MySqlTest.delete_row_from_Local_ad_switch("101010");//滨江
		MySqlTest.delete_row_from_Local_ad_switch("401011");//林周
		MySqlTest.delete_row_from_Local_ad_switch(region_id);		
		MySqlTest.insert_row_into_Local_ad_switch(region_id);
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("获取开屏广告及配置接口测试结束！");
	}
	
	@Test(dataProvider="ex")
	public void loginTest(String region_id, String excode, String exmsg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
        System.out.println(url);
        
        //Map<String, String> params = ParamsBuilt.params_built();
        Map<String, String> params = new HashMap<String, String>();
        params.put("region_id", region_id);

        System.out.println(params);
        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
        
        int code = Integer.parseInt(excode);
        
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.msg,exmsg);
        
        JSONObject adResult=final_res.data;
        JSONArray ads=adResult.getJSONArray("ad_disabled");
        
        List<Integer> adList=new ArrayList<>();
        for(int i=0;i<ads.size();i++) {
        		adList.add(ads.getInt(i));
        }
        
        String region_name=MySqlTest.select_name_from_local_region(region_id);
        
        if (!adList.isEmpty()) {
        	 if (adList.contains(1)) {
				System.out.println(region_name+"启动页不展示广告");
			}else {
				System.out.println(region_name+"启动页展示广告");
			}
        	 if (adList.contains(2)) {
 				System.out.println(region_name+"列表不展示广告");
 			}else {
 				System.out.println(region_name+"列表展示广告");
 			}
        	 if (adList.contains(3)) {
				System.out.println(region_name+"详情页不展示广告");
			}else {
				System.out.println(region_name+"详情页展示广告");
			}
		}else {
			System.out.println(region_name+"全部展示广告");
		}
        
      
	}	
}


